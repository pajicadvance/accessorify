package me.pajic.accessorify.mixin.compat.arselixirum;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.obscuria.elixirum.common.hooks.LivingEntityHooks;
import dev.obscuria.elixirum.registry.ElixirumItems;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("elixirum")
@Mixin(LivingEntityHooks.class)
public class LivingEntityHooksMixin {

    @ModifyExpressionValue(
            method = "checkTotemDeathProtection",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack tryConsumeTotemAccessory(ItemStack original, @Local(argsOnly = true) LivingEntity entity) {
        if (ModCommonConfig.totemOfUndyingAccessory) {
            ItemStack stack = ModUtil.getAccessoryStack(entity, ElixirumItems.WITCH_TOTEM_OF_UNDYING.value()).right();
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}
