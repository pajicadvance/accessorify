package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract void updateUsingItem(ItemStack usingItem);

    @ModifyExpressionValue(
            method = "checkTotemDeathProtection",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryConsumeTotemAccessory(ItemStack original) {
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
            ItemStack stack = ModUtil.tryGetTotemAccessory((LivingEntity) (Object) this);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }

    @WrapMethod(method = "updatingUsingItem")
    private void useSpyglassAccessory(Operation<Void> original) {
        if (ModUtil.shouldScope) {
            updateUsingItem(new ItemStack(Items.SPYGLASS));
        }
        else original.call();
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
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            ItemStack stack = ModUtil.tryGetElytraAccessory((LivingEntity) (Object) this).right();
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
    //?}
}
