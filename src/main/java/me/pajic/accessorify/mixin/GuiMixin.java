package me.pajic.accessorify.mixin;

import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >= 1.21.1
import net.minecraft.client.DeltaTracker;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void renderModOverlays(
            GuiGraphics guiGraphics,
            //? if >= 1.21.1
            DeltaTracker deltaTracker,
            //? if 1.20.1
            /*float partialTick,*/
            CallbackInfo ci
    ) {
        InfoOverlays.render(guiGraphics);
        ContextualSelectionWidget.render(guiGraphics);
    }
}
