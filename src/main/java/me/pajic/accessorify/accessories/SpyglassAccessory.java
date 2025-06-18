package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpyglassAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.SPYGLASS, new SpyglassAccessory());
    }

    @Override
    public String getPath() {
        return "add_belt";
    }

    @Override
    public String getSlot() {
        return "belt";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, Items.SPYGLASS);
    }
}
