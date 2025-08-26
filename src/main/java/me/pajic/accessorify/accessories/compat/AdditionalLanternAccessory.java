package me.pajic.accessorify.accessories.compat;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class AdditionalLanternAccessory implements SlotCopyingAccessory {

    public static void init() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.getNamespace().equals("additionallanterns") && rl.getPath().endsWith("_lantern")) {
                MultiVersionUtil.registerAccessory(item, new AdditionalLanternAccessory());
                ModUtil.LANTERNS.add(item);
            }
        });
    }

    @Override
    public String getPath() {
        return "add_belt_3";
    }

    @Override
    public String getSlot() {
        return "belt";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, ModUtil::isLantern);
    }
}
