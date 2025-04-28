package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

public class ArrowSelectionWidget {
    public static boolean widgetOpen = false;
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderArrowSelectionWidget(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (ModCommonConfig.arrowAccessory && MC.player != null && MC.level != null) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(MC.player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
                if (container != null) {
                    ExpandedSimpleContainer arrows = container.getAccessories();
                    if (!arrows.getItems().stream().allMatch(ItemStack::isEmpty)) {
                        if (arrows.getItem(ModScrollHandler.selectedArrowSlot).isEmpty()) {
                            do {
                                ModScrollHandler.selectedArrowSlot++;
                                if (ModScrollHandler.selectedArrowSlot >= arrows.getItems().size()) {
                                    ModScrollHandler.selectedArrowSlot = 0;
                                }
                            } while (arrows.getItem(ModScrollHandler.selectedArrowSlot).isEmpty());
                            PacketDistributor.sendToServer(new ModNetworking.C2SSyncArrowSlot(ModScrollHandler.selectedArrowSlot));
                        }
                        if (
                                ModUtil.isHoldingProjectileWeapon(MC.player) && !MC.player.isUsingItem() &&
                                MC.player.isShiftKeyDown() && !MC.options.hideGui && !ShulkerBoxSelectionWidget.widgetOpen
                        ) {
                            widgetOpen = true;
                            guiGraphics.flush();
                            RenderSystem.enableBlend();
                            WidgetUtil.renderCenterSlot(MC, guiGraphics);
                            arrows.forEach(arrow -> {
                                if (!arrow.getSecond().isEmpty()) {
                                    WidgetUtil.renderItemStack(
                                            MC, guiGraphics, arrow.getSecond(),
                                            arrow.getFirst() - ModScrollHandler.selectedArrowSlot
                                    );
                                }
                                if (arrow.getFirst() == ModScrollHandler.selectedArrowSlot) guiGraphics.drawString(
                                        MC.font, arrow.getSecond().getHoverName(),
                                        MC.getWindow().getGuiScaledWidth() / 2 - MC.font.width(arrow.getSecond().getHoverName()) / 2,
                                        MC.getWindow().getGuiScaledHeight() / 2 - 48,
                                        16777215
                                );
                            });
                            if (ModClientConfig.showUIHints) {
                                Component scrollHint = Component.translatable("gui.accessorify.hint_arrow_scroll");
                                Component exitHint = Component.translatable(
                                        "gui.accessorify.hint_arrow_exit",
                                        Component.keybind(MC.options.keyShift.getName())
                                );
                                guiGraphics.drawString(
                                        MC.font, scrollHint,
                                        MC.getWindow().getGuiScaledWidth() / 2 - MC.font.width(scrollHint) / 2,
                                        MC.getWindow().getGuiScaledHeight() / 2 + 12,
                                        16777215
                                );
                                guiGraphics.drawString(
                                        MC.font, exitHint,
                                        MC.getWindow().getGuiScaledWidth() / 2 - MC.font.width(exitHint) / 2,
                                        MC.getWindow().getGuiScaledHeight() / 2 + 24,
                                        16777215
                                );
                            }
                            guiGraphics.flush();
                            RenderSystem.disableBlend();
                        } else {
                            widgetOpen = false;
                        }
                    }
                }
            }
        }
    }
}
