package me.pajic.accessorify.gui;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
//$ expanded_simple_container
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
//? if >= 1.21.8 {
/*import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.core.component.DataComponents;
import net.minecraft.Util;
*///?}

import java.util.Optional;

public class ContextualSelectionWidget {
    public static boolean widgetOpen = false;
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderContextualSelectionWidget(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get() && MC.player != null && MC.level != null) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(MC.player);
            if (ac.isPresent()) {
                if (ModUtil.isHoldingProjectileWeapon(MC.player)) {
                    AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(MC.level, "arrow"));
                    if (container != null) {
                        /*? < 1.21.8 {*/ExpandedSimpleContainer/*?} else {*//*ExpandedContainer*//*?}*/ arrows = container.getAccessories();
                        if (!MultiVersionUtil.getItems(arrows).stream().allMatch(ItemStack::isEmpty)) {
                            if (arrows.getItem(ModScrollHandler.selectedArrowSlot).isEmpty()) {
                                do {
                                    ModScrollHandler.selectedArrowSlot++;
                                    if (ModScrollHandler.selectedArrowSlot >= MultiVersionUtil.getItems(arrows).size()) {
                                        ModScrollHandler.selectedArrowSlot = 0;
                                    }
                                } while (arrows.getItem(ModScrollHandler.selectedArrowSlot).isEmpty());
                                MultiVersionUtil.sendToServer(new ModNetworking.C2SSyncArrowSlot(ModScrollHandler.selectedArrowSlot));
                            }
                            if (widgetOpen || (ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get() && !MC.player.isUsingItem() && !MC.options.hideGui && ModKeybinds.OPEN_WIDGET.get().isDown())) {
                                if (ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get())
                                    widgetOpen = true;
                                MultiVersionUtil.startRender(guiGraphics);
                                WidgetUtil.renderCenterSlot(MC, guiGraphics);
                                arrows./*? < 1.21.8 {*/forEach(stack -> {/*?} else {*//*foreach((i, stack) -> {*//*?}*/
                                    int index = /*? < 1.21.8 {*/stack.getFirst()/*?} else {*//*i*//*?}*/;
                                    ItemStack arrow = /*? < 1.21.8 {*/stack.getSecond()/*?} else {*//*stack*//*?}*/;
                                    if (!arrow.isEmpty()) {
                                        WidgetUtil.renderItemStack(
                                                MC, guiGraphics, arrow,
                                                index - ModScrollHandler.selectedArrowSlot
                                        );
                                    }
                                    if (index == ModScrollHandler.selectedArrowSlot)
                                        WidgetUtil.renderCenterText(MC, arrow.getHoverName(), guiGraphics, -48);
                                });
                                if (ClientMain.CLIENT_CONFIG.widgetSettings.showUIHints.get()) {
                                    Component scrollHint = Component.translatable("gui.accessorify.hint_arrow_scroll");
                                    Component exitHint = Component.translatable(
                                            "gui.accessorify.hint_arrow_exit",
                                            Component.keybind(ModKeybinds.OPEN_WIDGET.get().getName())
                                    );
                                    WidgetUtil.renderCenterText(MC, scrollHint, guiGraphics, 12);
                                    WidgetUtil.renderCenterText(MC, exitHint, guiGraphics, 24);
                                }
                                MultiVersionUtil.stopRender(guiGraphics);
                            } else {
                                widgetOpen = false;
                            }
                        }
                    }
                } else {
                    AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(MC.level, "shulker"));
                    if (container != null) {
                        /*? < 1.21.8 {*/ExpandedSimpleContainer/*?} else {*//*ExpandedContainer*//*?}*/ shulkers = container.getAccessories();
                        int count = Math.toIntExact(MultiVersionUtil.getItems(shulkers).stream().filter(shulker -> !shulker.isEmpty()).count());
                        if (count > 0) {
                            if (shulkers.getItem(ModScrollHandler.selectedShulkerSlot).isEmpty()) {
                                do {
                                    ModScrollHandler.selectedShulkerSlot++;
                                    if (ModScrollHandler.selectedShulkerSlot >= MultiVersionUtil.getItems(shulkers).size()) {
                                        ModScrollHandler.selectedShulkerSlot = 0;
                                    }
                                } while (shulkers.getItem(ModScrollHandler.selectedShulkerSlot).isEmpty());
                                MultiVersionUtil.sendToServer(new ModNetworking.C2SSyncShulkerSlot(ModScrollHandler.selectedShulkerSlot));
                            }
                            if (widgetOpen || (ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get() && !MC.options.hideGui && ModKeybinds.OPEN_WIDGET.get().isDown())) {
                                if (count == 1) {
                                    MC.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                                    MultiVersionUtil.sendToServer(new ModNetworking.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                                    widgetOpen = false;
                                    if (ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get())
                                        ModKeybinds.OPEN_WIDGET.get().setDown(false);
                                } else {
                                    if (ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get())
                                        widgetOpen = true;
                                    MultiVersionUtil.startRender(guiGraphics);
                                    WidgetUtil.renderCenterSlot(MC, guiGraphics);
                                    shulkers./*? < 1.21.8 {*/forEach(stack -> {/*?} else {*//*foreach((i, stack) -> {*//*?}*/
                                        int index = /*? < 1.21.8 {*/stack.getFirst()/*?} else {*//*i*//*?}*/;
                                        ItemStack shulker = /*? < 1.21.8 {*/stack.getSecond()/*?} else {*//*stack*//*?}*/;
                                        if (!shulker.isEmpty()) WidgetUtil.renderItemStack(
                                                MC, guiGraphics, shulker,
                                                index - ModScrollHandler.selectedShulkerSlot
                                        );
                                        if (index == ModScrollHandler.selectedShulkerSlot)
                                            WidgetUtil.renderCenterText(MC, shulker.getHoverName(), guiGraphics, -48);
                                    });
                                    if (ClientMain.CLIENT_CONFIG.widgetSettings.showUIHints.get()) {
                                        Component scrollHint = Component.translatable("gui.accessorify.hint_shulker_scroll");
                                        Component tooltipHint = Component.translatable(
                                                "gui.accessorify.hint_shulker_tooltip",
                                                Component.keybind(MC.options.keyShift.getName())
                                        );
                                        Component exitHint = ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get() ? Component.translatable(
                                                "gui.accessorify.hint_shulker_exit_quick",
                                                Component.keybind(ModKeybinds.OPEN_WIDGET.get().getName())
                                        ) : Component.translatable(
                                                "gui.accessorify.hint_shulker_exit",
                                                Component.keybind(ModKeybinds.OPEN_WIDGET.get().getName())
                                        );
                                        WidgetUtil.renderCenterText(MC, scrollHint, guiGraphics, 12);
                                        WidgetUtil.renderCenterText(MC, tooltipHint, guiGraphics, 24);
                                        WidgetUtil.renderCenterText(MC, exitHint, guiGraphics, 36);
                                    }
                                    if (MC.player.isShiftKeyDown()) {
                                        ItemStack stack = shulkers.getItem(ModScrollHandler.selectedShulkerSlot);
                                        //? if < 1.21.8 {
                                        guiGraphics.renderTooltip(
                                                MC.font,
                                                stack,
                                                MC.getWindow().getGuiScaledWidth() / 2,
                                                MC.getWindow().getGuiScaledHeight() / 2
                                        );
                                        //?} else {
                                        /*guiGraphics.renderTooltip(
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
                                        *///?}
                                    }
                                    MultiVersionUtil.stopRender(guiGraphics);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
