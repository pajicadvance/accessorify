package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.accessorify.keybind.ModScrollHandler;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    //? if <= 1.21.1 {
    @WrapWithCondition(
            method = "onScroll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"
            )
    )
    private boolean redirectScroll(Inventory instance, double direction) {
        return ModScrollHandler.handleMouseScroll(instance, (int) Math.signum(direction));
    }
    //?}

    //? if > 1.21.1 {
    /*@WrapOperation(
            method = "onScroll",
            at = @At(
                    value = "INVOKE",
                    //? if < 1.21.8
                    target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedHotbarSlot(I)V"
                    //? if >= 1.21.8
                    /^target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V"^/
            )
    )
    private void redirectScroll(Inventory instance, int selectedHotbarSlot, Operation<Void> original, @Local int i) {
        if (ModScrollHandler.handleMouseScroll(instance, (int) Math.signum(i))) original.call(instance, selectedHotbarSlot);
    }
    *///?}
}
