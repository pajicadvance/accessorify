package me.pajic.accessorify.mixin.compat.aileron;

//? if < 1.21.10 {

/*import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lodestar.aileron.Aileron;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GameplayUtil;
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
        return original == ItemStack.EMPTY ? AccessoryUtil.getAccessoryStack(entity, GameplayUtil::isElytra) : original;
    }
}
*///?}
