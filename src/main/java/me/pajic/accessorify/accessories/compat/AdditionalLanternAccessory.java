package me.pajic.accessorify.accessories.compat;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;

public class AdditionalLanternAccessory implements SlotCopyingAccessory {
    @SubscribeEvent
    public static void init(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().getNamespace().equals("additionallanterns") && key.location().getPath().endsWith("_lantern")) {
                MultiVersionUtil.registerAccessory(value, new AdditionalLanternAccessory());
                ModUtil.LANTERNS.add(value);
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
