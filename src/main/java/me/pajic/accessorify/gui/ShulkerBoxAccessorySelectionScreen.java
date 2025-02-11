package me.pajic.accessorify.gui;

import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.network.ModNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShulkerBoxAccessorySelectionScreen extends Screen {

    private final List<SlotEntryReference> shulkerBoxes;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private ShulkerBoxWidget list;

    public ShulkerBoxAccessorySelectionScreen(List<SlotEntryReference> shulkerBoxes) {
        super(Component.translatable("screen.accessorify.shulkerBoxSelection.title"));
        this.shulkerBoxes = shulkerBoxes;
    }

    @Override
    protected void init() {
        LinearLayout linearLayout = layout.addToHeader(LinearLayout.horizontal().spacing(8));
        linearLayout.defaultCellSetting().alignHorizontallyCenter();
        linearLayout.addChild(new StringWidget(getTitle(), font));
        list = layout.addToContents(new ShulkerBoxWidget());
        LinearLayout linearLayout2 = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout2.addChild(new StringWidget(Component.translatable("screen.accessorify.shulkerBoxSelection.footer"), font));
        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
        list.updateSize(width, layout);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private class ShulkerBoxWidget extends ObjectSelectionList<ItemStackEntry> {

        public ShulkerBoxWidget() {
            super(
                    ShulkerBoxAccessorySelectionScreen.this.minecraft,
                    ShulkerBoxAccessorySelectionScreen.this.width,
                    ShulkerBoxAccessorySelectionScreen.this.height,
                    40,
                    24
            );

            shulkerBoxes.forEach(entry -> addEntry(new ItemStackEntry(entry.stack(), entry.reference().slot())));
            //setSelected(children().stream().findFirst().get());
            //centerScrollOn(children().stream().findFirst().get());
        }
    }

    private class ItemStackEntry extends ObjectSelectionList.Entry<ItemStackEntry> {

        private final ItemStack stack;
        private final int slot;

        public ItemStackEntry(ItemStack stack, int slot) {
            this.stack = stack;
            this.slot = slot;
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            guiGraphics.renderItem(stack, left + 5, top + 2);
            guiGraphics.drawString(
                    ShulkerBoxAccessorySelectionScreen.this.font,
                    stack.getHoverName(), left + 32, top + 6, 16777215
            );
            if (hovering) {
                guiGraphics.renderTooltip(
                        ShulkerBoxAccessorySelectionScreen.this.font,
                        stack, mouseX, mouseY
                );
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            PacketDistributor.sendToServer(new ModNetworking.C2SOpenShulkerBoxPayload(slot));
            onClose();
            return true;
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.translatable("narrator.select", stack.getHoverName());
        }
    }
}
