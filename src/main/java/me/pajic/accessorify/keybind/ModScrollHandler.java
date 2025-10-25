package me.pajic.accessorify.keybind;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
//$ expanded_simple_container
import io.wispforest.accessories.impl.core.ExpandedContainer;

import java.util.Optional;

public class ModScrollHandler {
    public static int selectedShulkerSlot = 0;
    public static int selectedArrowSlot = 0;

    public static boolean handleMouseScroll(Inventory inventory, int direction) {
        Player player = inventory.player;
        if (ClientMain.CLIENT_CONFIG.spyglassZoomSettings.scrollableZoom.get() && ModUtil.shouldScope) {
            if (direction != 0) {
                ModUtil.zoomModifier -= direction * (0.1F * ModUtil.zoomModifier);
                if (ModUtil.zoomModifier > 10) ModUtil.zoomModifier = 10;
                else if (ModUtil.zoomModifier < 0.1) ModUtil.zoomModifier = 0.1F;
                else player.playSound(SoundEvents.SPYGLASS_STOP_USING);
            }
            return false;
        } else if (Main.CONFIG.accessorySettings.arrowAccessory.get() && ModUtil.isHoldingProjectileWeapon(player) && ContextualSelectionWidget.widgetOpen) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(player, "arrow"));
                if (container != null) {
                    /*? if < 1.21.8 {*//*ExpandedSimpleContainer*//*?} else {*/ExpandedContainer/*?}*/ arrows = container.getAccessories();
                    if (!MultiVersionUtil.getItems(arrows).stream().allMatch(ItemStack::isEmpty)) {
                        int size = MultiVersionUtil.getItems(arrows).size();
                        do {
                            selectedArrowSlot -= direction;
                            if (selectedArrowSlot < 0) selectedArrowSlot = size - 1;
                            if (selectedArrowSlot >= size) selectedArrowSlot = 0;
                        } while (arrows.getItem(selectedArrowSlot).isEmpty());
                        MultiVersionUtil.C2S(new Payloads.C2SSyncArrowSlot(selectedArrowSlot));
                        return false;
                    }
                }
            }
        } else if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get() && ContextualSelectionWidget.widgetOpen) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(player, "shulker"));
                if (container != null) {
                    /*? if < 1.21.8 {*//*ExpandedSimpleContainer*//*?} else {*/ExpandedContainer/*?}*/ shulkers = container.getAccessories();
                    if (!MultiVersionUtil.getItems(shulkers).stream().allMatch(ItemStack::isEmpty)) {
                        int size = MultiVersionUtil.getItems(shulkers).size();
                        do {
                            selectedShulkerSlot -= direction;
                            if (selectedShulkerSlot < 0) selectedShulkerSlot = size - 1;
                            if (selectedShulkerSlot >= size) selectedShulkerSlot = 0;
                        } while (shulkers.getItem(selectedShulkerSlot).isEmpty());
                        MultiVersionUtil.C2S(new Payloads.C2SSyncShulkerSlot(selectedShulkerSlot));
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
