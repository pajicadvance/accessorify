package me.pajic.accessorify.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
//? if <= 1.21.1
//import net.minecraft.util.FastColor;
//? if > 1.21.1
import net.minecraft.util.ARGB;
//? if < 1.21.10
//import com.mojang.blaze3d.systems.RenderSystem;
//? if >= 1.21.10
import com.mojang.blaze3d.opengl.GlStateManager;

public class ClientUtil {
	public static boolean shouldScope = false;
	public static float zoomModifier = 1.0F;

	public static int color(int a, int r, int g, int b) {
		//? if <= 1.21.1
		//return FastColor.ARGB32.color(a, r, g, b);
		//? if > 1.21.1
		return ARGB.color(a, r, g, b);
	}

	public static int as8BitChannel(float value) {
		//? if > 1.21.1
		return ARGB.as8BitChannel(value);
		//? if 1.21.1
		//return FastColor.as8BitChannel(value);
		//? if 1.20.1
		//return Mth.floor(value * 255.0F);
	}

	public static boolean debugScreenShown() {
		//? if >= 1.21.1
		return Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen();
		//? if 1.20.1
		//return Minecraft.getInstance().options.renderDebug;
	}

	public static void startRender(GuiGraphics guiGraphics) {
		//? if < 1.21.10 {
        /*guiGraphics.flush();
        RenderSystem.enableBlend();
        *///?} else {
		guiGraphics.nextStratum();
		GlStateManager._enableBlend();
		//?}
	}

	public static void stopRender(GuiGraphics guiGraphics) {
		//? if < 1.21.10 {
        /*guiGraphics.flush();
        RenderSystem.disableBlend();
        *///?} else {
		GlStateManager._disableBlend();
		//?}
	}
}
