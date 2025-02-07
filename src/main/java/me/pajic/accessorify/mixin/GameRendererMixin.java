package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.accessorify.config.ModClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final Minecraft minecraft;
    //? if > 1.21.1
    /*@Shadow private float fovModifier;*/

    //? if <= 1.21.1 {
    @WrapWithCondition(
            method = "tickFov",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;fov:F",
                    ordinal = 7
            )
    )
    private boolean uncapSpyglassZoomLevel(GameRenderer instance, float value) {
        return !ModClientConfig.scrollableZoom || minecraft.player == null || !minecraft.player.isScoping();
    }
    //?}

    //? if > 1.21.1 {
    /*@ModifyExpressionValue(
            method = "tickFov",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;clamp(FFF)F"
            )
    )
    private float uncapSpyglassZoomLevel(float original) {
        if (ModClientConfig.scrollableZoom && minecraft.player != null && minecraft.player.isScoping()) {
            return fovModifier;
        }
        return original;
    }
    *///?}
}
