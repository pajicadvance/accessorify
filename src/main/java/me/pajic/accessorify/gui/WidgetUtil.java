package me.pajic.accessorify.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
//? if >= 1.21.4
/*import net.minecraft.client.renderer.RenderType;*/

public class WidgetUtil {
    public static void renderItemStack(Minecraft mc, GuiGraphics guiGraphics, ItemStack stack, int offset) {
        int stackX = mc.getWindow().getGuiScaledWidth() / 2 - 8 + 24 * offset;
        int stackY = mc.getWindow().getGuiScaledHeight() / 2 - 28;
        guiGraphics.renderFakeItem(stack, stackX, stackY);
        guiGraphics.renderItemDecorations(mc.font, stack, stackX, stackY);
    }

    public static void renderCenterSlot(Minecraft mc, GuiGraphics guiGraphics) {
        guiGraphics.blitSprite(
                //? if >= 1.21.4
                /*RenderType::guiTextured,*/
                ResourceLocation.withDefaultNamespace("hud/hotbar_offhand_right"),
                mc.getWindow().getGuiScaledWidth() / 2 - 18,
                mc.getWindow().getGuiScaledHeight() / 2 - 32,
                29, 24
        );
    }
}
