package me.pajic.accessorify.keybind;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import io.wispforest.accessories.impl.core.ExpandedContainer;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.AccessorifyClient;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.ClientUtil;
import me.pajic.accessorify.util.GameplayUtil;
import me.pajic.accessorify.util.NetworkUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ModScrollHandler {
    public static int selectedShulkerSlot = 0;
    public static int selectedArrowSlot = 0;

    public static boolean handleMouseScroll(Inventory inventory, int direction) {
        Player player = inventory.player;
        if (AccessorifyClient.CONFIG.spyglassZoomSettings.scrollableZoom.get() && ClientUtil.shouldScope) {
            if (direction != 0) {
                ClientUtil.zoomModifier -= direction * (0.1F * ClientUtil.zoomModifier);
                if (ClientUtil.zoomModifier > 10) ClientUtil.zoomModifier = 10;
                else if (ClientUtil.zoomModifier < 0.1) ClientUtil.zoomModifier = 0.1F;
                else player.playSound(SoundEvents.SPYGLASS_STOP_USING);
            }
            return false;
        } else if (Accessorify.CONFIG.accessorySettings.arrowAccessory.get() && GameplayUtil.isHoldingProjectileWeapon(player) && ContextualSelectionWidget.widgetOpen) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(player, "arrow"));
                if (container != null) {
					ExpandedContainer arrows = container.getAccessories();
                    if (!GameplayUtil.getContainerItems(arrows).stream().allMatch(ItemStack::isEmpty)) {
                        int size = GameplayUtil.getContainerItems(arrows).size();
                        do {
                            selectedArrowSlot -= direction;
                            if (selectedArrowSlot < 0) selectedArrowSlot = size - 1;
                            if (selectedArrowSlot >= size) selectedArrowSlot = 0;
                        } while (arrows.getItem(selectedArrowSlot).isEmpty());
                        NetworkUtil.C2S(new Payloads.C2SSyncArrowSlot(selectedArrowSlot));
                        return false;
                    }
                }
            }
        } else if (Accessorify.CONFIG.accessorySettings.shulkerBoxAccessory.get() && ContextualSelectionWidget.widgetOpen) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(player, "shulker"));
                if (container != null) {
					ExpandedContainer shulkers = container.getAccessories();
                    if (!GameplayUtil.getContainerItems(shulkers).stream().allMatch(ItemStack::isEmpty)) {
                        int size = GameplayUtil.getContainerItems(shulkers).size();
                        do {
                            selectedShulkerSlot -= direction;
                            if (selectedShulkerSlot < 0) selectedShulkerSlot = size - 1;
                            if (selectedShulkerSlot >= size) selectedShulkerSlot = 0;
                        } while (shulkers.getItem(selectedShulkerSlot).isEmpty());
                        NetworkUtil.C2S(new Payloads.C2SSyncShulkerSlot(selectedShulkerSlot));
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
