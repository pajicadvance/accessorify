package me.pajic.accessorify.mixin;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.booleans.BooleanObjectImmutablePair;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @ModifyExpressionValue(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/AbstractClientPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original, @Local(argsOnly = true) AbstractClientPlayer player) {
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            BooleanObjectImmutablePair<ItemStack> stack = ModUtil.tryGetElytraAccessory(player);
            if (!stack.leftBoolean()) return original;
            return stack.right().isEmpty() ? original : stack.right();
        }
        return original;
    }
}