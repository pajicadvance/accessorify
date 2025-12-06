package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.world.item.ItemStack;

public class LanternAccessory implements AccessoryExtension {

    @Override
    public String getAttributePath() {
        return "add_belt_1";
    }

    @Override
    public String getDefaultSlot() {
        return "belt";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference, GameplayUtil::isLantern);
    }
}
