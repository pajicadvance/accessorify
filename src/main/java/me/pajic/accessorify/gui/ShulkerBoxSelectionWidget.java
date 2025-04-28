package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.network.ModNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ShulkerBoxSelectionWidget {
    public static boolean widgetOpen = false;

    public static void initOverlay() {
        HudRenderCallback.EVENT.register(ShulkerBoxSelectionOverlay.INSTANCE::render);
    }

    public static class ShulkerBoxSelectionOverlay implements LayeredDraw.Layer {

        protected static final ShulkerBoxSelectionOverlay INSTANCE = new ShulkerBoxSelectionOverlay();
        private static final Minecraft MC = Minecraft.getInstance();

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
            if (Main.CONFIG.shulkerBoxAccessory() && MC.player != null && MC.level != null) {
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
                                ClientPlayNetworking.send(new ModNetworking.C2SSyncShulkerSlot(ModScrollHandler.selectedShulkerSlot));
                            }
                            if (widgetOpen || (Main.CONFIG.shulkerQuickSelect() && ModKeybinds.OPEN_SHULKER_BOX.isDown() && !ArrowSelectionWidget.widgetOpen)) {
                                if (count == 1) {
                                    MC.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                                    ClientPlayNetworking.send(new ModNetworking.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                                    widgetOpen = false;
                                    if (Main.CONFIG.shulkerQuickSelect()) ModKeybinds.OPEN_SHULKER_BOX.setDown(false);
                                } else {
                                    if (Main.CONFIG.shulkerQuickSelect()) widgetOpen = true;
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
                                    if (Main.CONFIG.showUIHints()) {
                                        Component scrollHint = Component.translatable("gui.accessorify.hint_shulker_scroll");
                                        Component tooltipHint = Component.translatable(
                                                "gui.accessorify.hint_shulker_tooltip",
                                                Component.keybind(MC.options.keyShift.getName())
                                        );
                                        Component exitHint = Main.CONFIG.shulkerQuickSelect() ? Component.translatable(
                                                "gui.accessorify.hint_shulker_exit_quick",
                                                Component.keybind(ModKeybinds.OPEN_SHULKER_BOX.getName())
                                        ) : Component.translatable(
                                                "gui.accessorify.hint_shulker_exit",
                                                Component.keybind(ModKeybinds.OPEN_SHULKER_BOX.getName())
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
}
