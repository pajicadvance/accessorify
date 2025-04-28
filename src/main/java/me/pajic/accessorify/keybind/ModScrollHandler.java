package me.pajic.accessorify.keybind;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.gui.ArrowSelectionWidget;
import me.pajic.accessorify.gui.ShulkerBoxSelectionWidget;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
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
        if (Main.CONFIG.spyglassZoom.scrollableZoom() && ModUtil.shouldScope) {
            if (direction != 0) {
                ModUtil.zoomModifier -= direction * (0.1F * ModUtil.zoomModifier);
                if (ModUtil.zoomModifier > 10) ModUtil.zoomModifier = 10;
                else if (ModUtil.zoomModifier < 0.1) ModUtil.zoomModifier = 0.1F;
                else player.playSound(SoundEvents.SPYGLASS_STOP_USING);
            }
            return false;
        } else if (Main.CONFIG.arrowAccessory() && ArrowSelectionWidget.widgetOpen) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
                if (container != null) {
                    ExpandedSimpleContainer arrows = container.getAccessories();
                    if (!arrows.getItems().stream().allMatch(ItemStack::isEmpty)) {
                        int size = arrows.getItems().size();
                        do {
                            selectedArrowSlot -= direction;
                            if (selectedArrowSlot < 0) selectedArrowSlot = size - 1;
                            if (selectedArrowSlot >= size) selectedArrowSlot = 0;
                        } while (arrows.getItem(selectedArrowSlot).isEmpty());
                        ClientPlayNetworking.send(new ModNetworking.C2SSyncArrowSlot(selectedArrowSlot));
                        return false;
                    }
                }
            }
        } else if (Main.CONFIG.shulkerBoxAccessory() && ShulkerBoxSelectionWidget.widgetOpen) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("shulker"));
                if (container != null) {
                    ExpandedSimpleContainer shulkers = container.getAccessories();
                    if (!shulkers.getItems().stream().allMatch(ItemStack::isEmpty)) {
                        int size = shulkers.getItems().size();
                        do {
                            selectedShulkerSlot -= direction;
                            if (selectedShulkerSlot < 0) selectedShulkerSlot = size - 1;
                            if (selectedShulkerSlot >= size) selectedShulkerSlot = 0;
                        } while (shulkers.getItem(selectedShulkerSlot).isEmpty());
                        ClientPlayNetworking.send(new ModNetworking.C2SSyncShulkerSlot(selectedShulkerSlot));
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
