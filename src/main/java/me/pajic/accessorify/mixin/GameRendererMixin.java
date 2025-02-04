package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.accessorify.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final Minecraft minecraft;

    @WrapWithCondition(
            method = "tickFov",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;fov:F",
                    ordinal = 7
            )
    )
    private boolean uncapSpyglassZoomLevel(GameRenderer instance, float value) {
        return !Main.CONFIG.spyglassZoom.scrollableZoom() || minecraft.player == null || !minecraft.player.isScoping();
    }
}
