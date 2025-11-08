package me.pajic.accessorify.mixin.compat.aileron;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lodestar.aileron.Aileron;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("aileron")
@Mixin(Aileron.class)
public class AileronMixin {

    @ModifyReturnValue(
            method = "getElytra",
            at = @At("RETURN")
    )
    private static ItemStack findElytraAccessory(ItemStack original, @Local(argsOnly = true) LivingEntity entity) {
        return original == ItemStack.EMPTY ? ModUtil.tryGetElytraAccessory(entity).right() : original;
    }
}
