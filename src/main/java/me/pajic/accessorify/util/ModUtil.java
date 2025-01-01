package me.pajic.accessorify.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModUtil {

    public static boolean shouldScope = false;

    public static ItemStack getAccessoryStack(LivingEntity entity, Item item) {
        AccessoriesCapability ac = AccessoriesCapability.get(entity);
        if (ac != null && ac.isEquipped(item)) {
            SlotEntryReference itemRef = ac.getFirstEquipped(item);
            if (itemRef != null) {
                return ac.getContainer(itemRef.reference().type()).getAccessories().getItem(0);
            }
        }
        return ItemStack.EMPTY;
    }
}
