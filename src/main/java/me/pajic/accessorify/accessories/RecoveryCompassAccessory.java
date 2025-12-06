package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RecoveryCompassAccessory implements AccessoryExtension {

    @Override
    public String getAttributePath() {
        return "add_wrist_3";
    }

    @Override
    public String getDefaultSlot() {
        return "wrist";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference, Items.RECOVERY_COMPASS);
    }
}
