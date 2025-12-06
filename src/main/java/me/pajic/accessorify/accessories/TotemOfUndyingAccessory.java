package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.world.item.ItemStack;

public class TotemOfUndyingAccessory implements AccessoryExtension {

    @Override
    public String getAttributePath() {
        return "add_charm";
    }

    @Override
    public String getDefaultSlot() {
        return "charm";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference, GameplayUtil::isTotem);
    }
}
