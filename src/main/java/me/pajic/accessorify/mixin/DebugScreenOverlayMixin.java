package me.pajic.accessorify.mixin;

import com.google.common.base.Strings;
import me.pajic.accessorify.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugScreenOverlayMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(
            method = "renderLines",
            at = @At("HEAD")
    )
    private void filterLines(GuiGraphics guiGraphics, List<String> lines, boolean leftSide, CallbackInfo ci) {
        if (Main.CONFIG.hideDebugInfoInSurvival.get() && minecraft.showOnlyReducedInfo()) {
            lines.removeIf(text -> !Strings.isNullOrEmpty(text) && text.startsWith(/*? < 1.21.10 {*//*"Chunk-relative: "*//*?} else {*/"Section-relative: "/*?}*/));
            lines.removeIf(text -> !Strings.isNullOrEmpty(text) && text.startsWith("hunger: "));
        }
    }
}
