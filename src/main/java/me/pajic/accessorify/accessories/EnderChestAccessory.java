package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EnderChestAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.ENDER_CHEST, new EnderChestAccessory());
    }

    @Override
    public String getPath() {
        return "add_back";
    }

    @Override
    public String getSlot() {
        return "back";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, Items.ENDER_CHEST);
    }
}
