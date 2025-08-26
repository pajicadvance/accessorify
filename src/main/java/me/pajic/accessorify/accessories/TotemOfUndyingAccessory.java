package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TotemOfUndyingAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.TOTEM_OF_UNDYING, new TotemOfUndyingAccessory());
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        MultiVersionUtil.noRenderer(Items.TOTEM_OF_UNDYING);
    }

    @Override
    public String getPath() {
        return "add_charm";
    }

    @Override
    public String getSlot() {
        return "charm";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, ModUtil::isTotem);
    }
}
