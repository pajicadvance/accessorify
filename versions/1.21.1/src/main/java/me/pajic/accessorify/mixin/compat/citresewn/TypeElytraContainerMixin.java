package me.pajic.accessorify.mixin.compat.citresewn;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shcm.shsupercm.fabric.citresewn.defaults.cit.types.TypeElytra;

@IfModLoaded("citresewn")
@Mixin(TypeElytra.Container.class)
public class TypeElytraContainerMixin {

    @ModifyExpressionValue(
            method = "getVisualElytraItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original, @Local(argsOnly = true) LivingEntity entity) {
        if (Main.CONFIG.elytraAccessory()) {
            ItemStack stack = ModUtil.tryGetElytraAccessory(entity);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}
