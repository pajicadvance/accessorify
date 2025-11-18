package me.pajic.accessorify.mixin.compat.netheriteextras;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import xyz.hafemann.netheriteextras.event.ModEvents;
import xyz.hafemann.netheriteextras.item.ModItems;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.injection.At;
@IfModLoaded("netheriteextras")
@Mixin(ModEvents.class)
public class ModEventsMixin {

    @ModifyExpressionValue(
            method = "lambda$registerModEvents$1",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack tryConsumeTotemAccessory(ItemStack original, @Local(argsOnly = true) LivingEntity livingEntity) {
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
            ItemStack itemStack = ModUtil.getAccessoryStack(livingEntity, ModItems.TOTEM_OF_NEVERDYING).right();
            return itemStack.isEmpty() ? original : itemStack;
        }
        return original;
    }
}