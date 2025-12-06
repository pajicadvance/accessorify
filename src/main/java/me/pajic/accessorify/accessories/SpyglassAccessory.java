package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SpyglassAccessory implements AccessoryExtension {

    @Override
    public String getAttributePath() {
        return "add_belt";
    }

    @Override
    public String getDefaultSlot() {
        return "belt";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference, Items.SPYGLASS);
    }
}
