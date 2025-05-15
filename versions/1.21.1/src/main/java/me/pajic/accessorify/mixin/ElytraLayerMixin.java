package me.pajic.accessorify.mixin;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.booleans.BooleanObjectImmutablePair;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {

    @ModifyExpressionValue(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private <T extends LivingEntity> ItemStack tryGetElytraAccessory(ItemStack original, @Local(argsOnly = true) T livingEntity) {
        if (ModCommonConfig.elytraAccessory) {
            BooleanObjectImmutablePair<ItemStack> stack = ModUtil.tryGetElytraAccessory(livingEntity);
            if (!stack.leftBoolean()) return original;
            return stack.right().isEmpty() ? original : stack.right();
        }
        return original;
    }
}