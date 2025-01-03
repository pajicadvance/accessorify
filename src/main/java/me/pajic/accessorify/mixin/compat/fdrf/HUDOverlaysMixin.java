package me.pajic.accessorify.mixin.compat.fdrf;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vectorwing.farmersdelight.client.gui.HUDOverlays;

@IfModLoaded("farmersdelight")
@Mixin(HUDOverlays.class)
public class HUDOverlaysMixin {

    @Redirect(
            method = "drawNourishmentOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"
            )
    )
    private static void disableDepthTest1(@Local(argsOnly = true) GuiGraphics guiGraphics) {
        guiGraphics.flush();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
    }

    @Redirect(
            method = "drawComfortOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"
            )
    )
    private static void disableDepthTest2(@Local(argsOnly = true) GuiGraphics guiGraphics) {
        guiGraphics.flush();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
    }

    @Redirect(
            method = "drawNourishmentOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"
            )
    )
    private static void enableDepthTest1(@Local(argsOnly = true) GuiGraphics guiGraphics) {
        guiGraphics.flush();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    @Redirect(
            method = "drawComfortOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"
            )
    )
    private static void enableDepthTest2(@Local(argsOnly = true) GuiGraphics guiGraphics) {
        guiGraphics.flush();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
}