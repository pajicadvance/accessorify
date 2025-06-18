package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CompassAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.COMPASS, new CompassAccessory());
    }

    @Override
    public String getPath() {
        return "add_wrist_1";
    }

    @Override
    public String getSlot() {
        return "wrist";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, Items.COMPASS);
    }
}
