package me.pajic.accessorify.mixin.compat.deeperdarker;
import com.kyanite.deeperdarker.client.render.SoulElytraRenderer;
import com.kyanite.deeperdarker.content.DDItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("deeperdarker")
@Mixin(SoulElytraRenderer.class)
public class SoulElytraRendererMixin {

    @ModifyExpressionValue(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private <E extends LivingEntity> ItemStack tryGetSoulElytraAccessory(ItemStack original, @Local(argsOnly = true) E entity) {
        if (ModCommonConfig.elytraAccessory) {
            ItemStack stack = ModUtil.getAccessoryStack(entity, DDItems.SOUL_ELYTRA.get());
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}