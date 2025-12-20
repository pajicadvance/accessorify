package me.pajic.accessorify.gui;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.AccessorifyClient;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.ClientUtil;
import me.pajic.accessorify.util.GameplayUtil;
import me.pajic.accessorify.util.NetworkUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import io.wispforest.accessories.impl.core.ExpandedContainer;
//? if > 1.21.1 {
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.core.component.DataComponents;
import net.minecraft.Util;
//?}

import java.util.Optional;

public class ContextualSelectionWidget {
    public static boolean widgetOpen = false;

    private static final Minecraft MC = Minecraft.getInstance();

    public static void render(GuiGraphics guiGraphics) {
        if (MC.player != null && MC.level != null) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(MC.player);
            if (ac.isPresent()) {
                if (Accessorify.CONFIG.accessorySettings.arrowAccessory.get() && GameplayUtil.isHoldingProjectileWeapon(MC.player)) {
                    AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(MC.level, "arrow"));
                    if (container != null) {
						ExpandedContainer arrows = container.getAccessories();
                        if (!GameplayUtil.getContainerItems(arrows).stream().allMatch(ItemStack::isEmpty)) {
                            if (arrows.getItem(ModScrollHandler.selectedArrowSlot).isEmpty()) {
                                do {
                                    ModScrollHandler.selectedArrowSlot++;
                                    if (ModScrollHandler.selectedArrowSlot >= GameplayUtil.getContainerItems(arrows).size()) {
                                        ModScrollHandler.selectedArrowSlot = 0;
                                    }
                                } while (arrows.getItem(ModScrollHandler.selectedArrowSlot).isEmpty());
                                NetworkUtil.C2S(new Payloads.C2SSyncArrowSlot(ModScrollHandler.selectedArrowSlot));
                            }
                            if (widgetOpen || (AccessorifyClient.CONFIG.widgetSettings.quickSelect.get() && !MC.player.isUsingItem() && !MC.options.hideGui && ModKeybinds.OPEN_WIDGET.isDown())) {
                                if (AccessorifyClient.CONFIG.widgetSettings.quickSelect.get())
                                    widgetOpen = true;
                                ClientUtil.startRender(guiGraphics);
                                WidgetUtil.renderCenterSlot(MC, guiGraphics);
                                arrows./*? if < 1.21.10 {*//*forEach(stack -> {*//*?} else {*/foreach((i, stack) -> {/*?}*/
                                    int index = /*? if < 1.21.10 {*//*stack.getFirst()*//*?} else {*/i/*?}*/;
                                    ItemStack arrow = /*? if < 1.21.10 {*//*stack.getSecond()*//*?} else {*/stack/*?}*/;
                                    if (!arrow.isEmpty()) {
                                        WidgetUtil.renderItemStack(
                                                MC, guiGraphics, arrow,
                                                index - ModScrollHandler.selectedArrowSlot
                                        );
                                    }
                                    if (index == ModScrollHandler.selectedArrowSlot)
                                        WidgetUtil.renderCenterText(MC, arrow.getHoverName(), guiGraphics, -48);
                                });
                                if (AccessorifyClient.CONFIG.widgetSettings.showUIHints.get()) {
                                    Component scrollHint = Component.translatable("gui.accessorify.hint_arrow_scroll");
                                    Component exitHint = Component.translatable(
                                            "gui.accessorify.hint_arrow_exit",
                                            Component.keybind(ModKeybinds.OPEN_WIDGET.getName())
                                    );
                                    WidgetUtil.renderCenterText(MC, scrollHint, guiGraphics, 12);
                                    WidgetUtil.renderCenterText(MC, exitHint, guiGraphics, 24);
                                }
                                ClientUtil.stopRender(guiGraphics);
                            } else {
                                widgetOpen = false;
                            }
                        }
                    }
                } else if (Accessorify.CONFIG.accessorySettings.shulkerBoxAccessory.get()) {
                    AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(MC.level, "shulker"));
                    if (container != null) {
						ExpandedContainer shulkers = container.getAccessories();
                        int count = Math.toIntExact(GameplayUtil.getContainerItems(shulkers).stream().filter(shulker -> !shulker.isEmpty()).count());
                        if (count > 0) {
                            if (shulkers.getItem(ModScrollHandler.selectedShulkerSlot).isEmpty()) {
                                do {
                                    ModScrollHandler.selectedShulkerSlot++;
                                    if (ModScrollHandler.selectedShulkerSlot >= GameplayUtil.getContainerItems(shulkers).size()) {
                                        ModScrollHandler.selectedShulkerSlot = 0;
                                    }
                                } while (shulkers.getItem(ModScrollHandler.selectedShulkerSlot).isEmpty());
                                NetworkUtil.C2S(new Payloads.C2SSyncShulkerSlot(ModScrollHandler.selectedShulkerSlot));
                            }
                            if (widgetOpen || (AccessorifyClient.CONFIG.widgetSettings.quickSelect.get() && !MC.options.hideGui && ModKeybinds.OPEN_WIDGET.isDown())) {
                                if (count == 1) {
                                    MC.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                                    NetworkUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                                    widgetOpen = false;
                                    if (AccessorifyClient.CONFIG.widgetSettings.quickSelect.get())
                                        ModKeybinds.OPEN_WIDGET.setDown(false);
                                } else {
                                    if (AccessorifyClient.CONFIG.widgetSettings.quickSelect.get())
                                        widgetOpen = true;
                                    ClientUtil.startRender(guiGraphics);
                                    WidgetUtil.renderCenterSlot(MC, guiGraphics);
                                    shulkers./*? if < 1.21.10 {*//*forEach(stack -> {*//*?} else {*/foreach((i, stack) -> {/*?}*/
                                        int index = /*? if < 1.21.10 {*//*stack.getFirst()*//*?} else {*/i/*?}*/;
                                        ItemStack shulker = /*? if < 1.21.10 {*//*stack.getSecond()*//*?} else {*/stack/*?}*/;
                                        if (!shulker.isEmpty()) WidgetUtil.renderItemStack(
                                                MC, guiGraphics, shulker,
                                                index - ModScrollHandler.selectedShulkerSlot
                                        );
                                        if (index == ModScrollHandler.selectedShulkerSlot)
                                            WidgetUtil.renderCenterText(MC, shulker.getHoverName(), guiGraphics, -48);
                                    });
                                    if (AccessorifyClient.CONFIG.widgetSettings.showUIHints.get()) {
                                        Component scrollHint = Component.translatable("gui.accessorify.hint_shulker_scroll");
                                        Component tooltipHint = Component.translatable(
                                                "gui.accessorify.hint_shulker_tooltip",
                                                Component.keybind(MC.options.keyShift.getName())
                                        );
                                        Component exitHint = AccessorifyClient.CONFIG.widgetSettings.quickSelect.get() ? Component.translatable(
                                                "gui.accessorify.hint_shulker_exit_quick",
                                                Component.keybind(ModKeybinds.OPEN_WIDGET.getName())
                                        ) : Component.translatable(
                                                "gui.accessorify.hint_shulker_exit",
                                                Component.keybind(ModKeybinds.OPEN_WIDGET.getName())
                                        );
                                        WidgetUtil.renderCenterText(MC, scrollHint, guiGraphics, 12);
                                        WidgetUtil.renderCenterText(MC, tooltipHint, guiGraphics, 24);
                                        WidgetUtil.renderCenterText(MC, exitHint, guiGraphics, 36);
                                    }
                                    if (MC.player.isShiftKeyDown()) {
                                        ItemStack stack = shulkers.getItem(ModScrollHandler.selectedShulkerSlot);
                                        //? if < 1.21.10 {
                                        /*guiGraphics.renderTooltip(
                                                MC.font,
                                                stack,
                                                MC.getWindow().getGuiScaledWidth() / 2,
                                                MC.getWindow().getGuiScaledHeight() / 2
                                        );
                                        *///?} else {
                                        guiGraphics.renderTooltip(
                                                MC.font,
                                                Screen.getTooltipFromItem(MC, stack).stream()
                                                        .map(Component::getVisualOrderText)
                                                        .map(ClientTooltipComponent::create)
                                                        .collect(Util.toMutableList()),
                                                MC.getWindow().getGuiScaledWidth() / 2,
                                                MC.getWindow().getGuiScaledHeight() / 2,
                                                DefaultTooltipPositioner.INSTANCE,
                                                stack.get(DataComponents.TOOLTIP_STYLE)
                                        );
                                        //?}
                                    }
                                    ClientUtil.stopRender(guiGraphics);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
