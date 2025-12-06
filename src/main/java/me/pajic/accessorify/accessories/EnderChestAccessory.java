package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EnderChestAccessory implements AccessoryExtension {

    @Override
    public String getAttributePath() {
        return "add_back";
    }

    @Override
    public String getDefaultSlot() {
        return "back";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference, Items.ENDER_CHEST);
    }
}
