package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ArrowSelectionWidget {
    public static boolean widgetOpen = false;

    public static void initOverlay() {
        HudRenderCallback.EVENT.register(ArrowSelectionOverlay.INSTANCE::render);
    }

    public static class ArrowSelectionOverlay implements LayeredDraw.Layer {

        protected static final ArrowSelectionOverlay INSTANCE = new ArrowSelectionOverlay();
        private static final Minecraft MC = Minecraft.getInstance();

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
            if (Main.CONFIG.arrowAccessory() && MC.player != null && MC.level != null) {
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
                                ClientPlayNetworking.send(new ModNetworking.C2SSyncArrowSlot(ModScrollHandler.selectedArrowSlot));
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
                                    if (arrow.getFirst() == ModScrollHandler.selectedArrowSlot)
                                        WidgetUtil.renderCenterText(MC, arrow.getSecond().getHoverName(), guiGraphics, -48);
                                });
                                if (Main.CONFIG.showUIHints()) {
                                    Component scrollHint = Component.translatable("gui.accessorify.hint_arrow_scroll");
                                    Component exitHint = Component.translatable(
                                            "gui.accessorify.hint_arrow_exit",
                                            Component.keybind(MC.options.keyShift.getName())
                                    );
                                    WidgetUtil.renderCenterText(MC, scrollHint, guiGraphics, 12);
                                    WidgetUtil.renderCenterText(MC, exitHint, guiGraphics, 24);
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
}
