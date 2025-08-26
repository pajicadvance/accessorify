package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RecoveryCompassAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.RECOVERY_COMPASS, new RecoveryCompassAccessory());
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        MultiVersionUtil.noRenderer(Items.RECOVERY_COMPASS);
    }

    @Override
    public String getPath() {
        return "add_wrist_3";
    }

    @Override
    public String getSlot() {
        return "wrist";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, Items.RECOVERY_COMPASS);
    }
}
