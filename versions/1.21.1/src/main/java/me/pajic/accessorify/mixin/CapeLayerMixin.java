package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.item.ElytraItem;

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
        if (Main.CONFIG.elytraAccessory()) {
            ItemStack stack = ModUtil.tryGetElytraAccessory(player);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean modifyElytraCheck(boolean original, @Local ItemStack itemStack, @Local(argsOnly = true) AbstractClientPlayer player) {
        if (
                !ModUtil.tryGetElytraAccessory(player).isEmpty() &&
                !(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)
        ) {
            return itemStack.getItem() instanceof ElytraItem;
        }
        return original;
    }
}
