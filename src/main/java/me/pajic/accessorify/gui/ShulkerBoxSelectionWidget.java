package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;

public class ShulkerBoxSelectionWidget {
    public static boolean widgetOpen = false;
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderShulkerBoxSelectionWidget(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get() && MC.player != null && MC.level != null) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(MC.player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("shulker"));
                if (container != null) {
                    ExpandedSimpleContainer shulkers = container.getAccessories();
                    int count = Math.toIntExact(shulkers.getItems().stream().filter(shulker -> !shulker.isEmpty()).count());
                    if (count > 0) {
                        if (shulkers.getItem(ModScrollHandler.selectedShulkerSlot).isEmpty()) {
                            do {
                                ModScrollHandler.selectedShulkerSlot++;
                                if (ModScrollHandler.selectedShulkerSlot >= shulkers.getItems().size()) {
                                    ModScrollHandler.selectedShulkerSlot = 0;
                                }
                            } while (shulkers.getItem(ModScrollHandler.selectedShulkerSlot).isEmpty());
                            PacketDistributor.sendToServer(new ModNetworking.C2SSyncShulkerSlot(ModScrollHandler.selectedShulkerSlot));
                        }
                        if (widgetOpen || (ClientMain.CLIENT_CONFIG.widgetSettings.shulkerQuickSelect.get() && ModKeybinds.OPEN_SHULKER_BOX.get().isDown() && !ArrowSelectionWidget.widgetOpen)) {
                            if (count == 1) {
                                MC.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                                PacketDistributor.sendToServer(new ModNetworking.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                                widgetOpen = false;
                                if (ClientMain.CLIENT_CONFIG.widgetSettings.shulkerQuickSelect.get()) ModKeybinds.OPEN_SHULKER_BOX.get().setDown(false);
                            } else {
                                if (ClientMain.CLIENT_CONFIG.widgetSettings.shulkerQuickSelect.get()) widgetOpen = true;
                                guiGraphics.flush();
                                RenderSystem.enableBlend();
                                WidgetUtil.renderCenterSlot(MC, guiGraphics);
                                shulkers.forEach(shulker -> {
                                    if (!shulker.getSecond().isEmpty()) WidgetUtil.renderItemStack(
                                            MC, guiGraphics, shulker.getSecond(),
                                            shulker.getFirst() - ModScrollHandler.selectedShulkerSlot
                                    );
                                    if (shulker.getFirst() == ModScrollHandler.selectedShulkerSlot)
                                        WidgetUtil.renderCenterText(MC, shulker.getSecond().getHoverName(), guiGraphics, -48);
                                });
                                if (MC.player.isShiftKeyDown()) {
                                    guiGraphics.renderTooltip(
                                            MC.font, shulkers.getItem(ModScrollHandler.selectedShulkerSlot),
                                            MC.getWindow().getGuiScaledWidth() / 2,
                                            MC.getWindow().getGuiScaledHeight() / 2
                                    );
                                }
                                if (ClientMain.CLIENT_CONFIG.widgetSettings.showUIHints.get()) {
                                    Component scrollHint = Component.translatable("gui.accessorify.hint_shulker_scroll");
                                    Component tooltipHint = Component.translatable(
                                            "gui.accessorify.hint_shulker_tooltip",
                                            Component.keybind(MC.options.keyShift.getName())
                                    );
                                    Component exitHint = ClientMain.CLIENT_CONFIG.widgetSettings.shulkerQuickSelect.get() ? Component.translatable(
                                            "gui.accessorify.hint_shulker_exit_quick",
                                            Component.keybind(ModKeybinds.OPEN_SHULKER_BOX.get().getName())
                                    ) : Component.translatable(
                                            "gui.accessorify.hint_shulker_exit",
                                            Component.keybind(ModKeybinds.OPEN_SHULKER_BOX.get().getName())
                                    );
                                    WidgetUtil.renderCenterText(MC, scrollHint, guiGraphics, 12);
                                    WidgetUtil.renderCenterText(MC, tooltipHint, guiGraphics, 24);
                                    WidgetUtil.renderCenterText(MC, exitHint, guiGraphics, 36);
                                }
                                guiGraphics.flush();
                                RenderSystem.disableBlend();
                            }
                        }
                    }
                }
            }
        }
    }
}
