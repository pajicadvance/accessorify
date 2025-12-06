package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.core.Accessory;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxAccessory implements Accessory {

    @Override
    public int maxStackSize(ItemStack stack) {
        return 1;
    }
}
