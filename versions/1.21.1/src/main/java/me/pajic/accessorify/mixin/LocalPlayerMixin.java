package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (Main.CONFIG.elytraAccessory()) {
            ItemStack stack = ModUtil.tryGetElytraAccessory((LivingEntity) (Object) this);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean modifyElytraCheck(boolean original, @Local ItemStack itemStack) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (
                !ModUtil.tryGetElytraAccessory(livingEntity).isEmpty() &&
                !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)
        ) {
            return itemStack.getItem() instanceof ElytraItem;
        }
        return original;
    }
}
