package me.pajic.accessorify.util.compat;

import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfshulker.item.ModItems;
import me.pajic.accessorify.menu.ShulkerBoxAccessoryContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.Map;

public class ReinfShulkerCompat {
    public static int getInventorySizeForReinfShulker(Item shulker) {
        for (Map.Entry<ReinforcingMaterial, Map<DyeColor, Item>> entry : ModItems.REINFORCED_SHULKER_BOX_MAP.entrySet()) {
            if (entry.getValue().containsValue(shulker)) return entry.getKey().getSize();
        }
        return 0;
    }

    public static AbstractContainerMenu createMenu(int syncId, Inventory inventory, ShulkerBoxAccessoryContainerMenu menu, Item shulker) {
        ReinforcingMaterial material = null;
        for (Map.Entry<ReinforcingMaterial, Map<DyeColor, Item>> entry : ModItems.REINFORCED_SHULKER_BOX_MAP.entrySet()) {
            if (entry.getValue().containsValue(shulker)) {
                material = entry.getKey();
                break;
            }
        }
        return material != null ? ReinforcedStorageScreenHandler.createShulkerBoxScreen(material, syncId, inventory, menu) : new ShulkerBoxMenu(syncId, inventory, menu);
    }
}
