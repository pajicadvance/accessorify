package me.pajic.accessorify.gui;

import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
//? if >= 1.21.8
import net.minecraft.client.renderer.RenderPipelines;

public class WidgetUtil {
    public static void renderItemStack(Minecraft mc, GuiGraphics guiGraphics, ItemStack stack, int offset) {
        int stackX = mc.getWindow().getGuiScaledWidth() / 2 - 8 + 24 * offset;
        int stackY = mc.getWindow().getGuiScaledHeight() / 2 - 28;
        guiGraphics.renderFakeItem(stack, stackX, stackY);
        guiGraphics.renderItemDecorations(mc.font, stack, stackX, stackY);
    }

    public static void renderCenterSlot(Minecraft mc, GuiGraphics guiGraphics) {
        //? if >= 1.21.1 {
        guiGraphics.blitSprite(
                //? if >= 1.21.8
                RenderPipelines.GUI_TEXTURED,
                MultiVersionUtil.parse("hud/hotbar_offhand_right"),
                mc.getWindow().getGuiScaledWidth() / 2 - 18,
                mc.getWindow().getGuiScaledHeight() / 2 - 32,
                29, 24
        );
        //?} else {
        /*guiGraphics.blit(
                MultiVersionUtil.parse("textures/gui/widgets.png"),
                mc.getWindow().getGuiScaledWidth() / 2 - 18,
                mc.getWindow().getGuiScaledHeight() / 2 - 32,
                53, 22, 29, 24
        );
        *///?}
    }

    public static void renderCenterText(Minecraft mc, Component text, GuiGraphics guiGraphics, int offset) {
        guiGraphics.drawString(
                mc.font, text,
                mc.getWindow().getGuiScaledWidth() / 2 - mc.font.width(text) / 2,
                mc.getWindow().getGuiScaledHeight() / 2 + offset,
                0xffffffff
        );
    }
}
