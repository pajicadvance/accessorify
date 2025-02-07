package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    //? if <= 1.21.1 {
    @WrapWithCondition(
            method = "onScroll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"
            )
    )
    private boolean redirectScrollIfScoping(Inventory instance, double direction) {
        if (ModClientConfig.scrollableZoom && minecraft.player != null && ModUtil.shouldScope) {
            int d = (int) Math.signum(direction);
            if (d != 0) {
                ModUtil.zoomModifier -= d * (0.1F * ModUtil.zoomModifier);
                if (ModUtil.zoomModifier > 10) ModUtil.zoomModifier = 10;
                else if (ModUtil.zoomModifier < 0.1) ModUtil.zoomModifier = 0.1F;
                else minecraft.player.playSound(SoundEvents.SPYGLASS_STOP_USING);
            }
            return false;
        }
        return true;
    }
    //?}

    //? if > 1.21.1 {
    /*@WrapOperation(
            method = "onScroll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedHotbarSlot(I)V"
            )
    )
    private void redirectScrollIfScoping(Inventory instance, int selectedHotbarSlot, Operation<Void> original, @Local int i) {
        if (ModClientConfig.scrollableZoom && minecraft.player != null && ModUtil.shouldScope) {
            int d = (int) Math.signum(i);
            if (d != 0) {
                ModUtil.zoomModifier -= d * (0.1F * ModUtil.zoomModifier);
                if (ModUtil.zoomModifier > 10) ModUtil.zoomModifier = 10;
                else if (ModUtil.zoomModifier < 0.1) ModUtil.zoomModifier = 0.1F;
                else minecraft.player.playSound(SoundEvents.SPYGLASS_STOP_USING);
            }
        }
        else original.call(instance, selectedHotbarSlot);
    }
    *///?}
}
