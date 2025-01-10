package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if <= 1.21.1
import net.minecraft.world.item.ElytraItem;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyExpressionValue(
            method = "checkTotemDeathProtection",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryConsumeTotemAccessory(ItemStack original) {
        if (ModCommonConfig.totemOfUndyingAccessory) {
            ItemStack stack = ModUtil.getAccessoryStack((LivingEntity) (Object) this, Items.TOTEM_OF_UNDYING);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }

    //? if <= 1.21.1 {
    @ModifyExpressionValue(
            method = "updateFallFlying",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (ModCommonConfig.elytraAccessory) {
            ItemStack stack = ModUtil.tryGetElytraAccessory((LivingEntity) (Object) this);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
    //?}
}
