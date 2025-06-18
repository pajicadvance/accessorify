package me.pajic.accessorify.accessories.compat;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import sereneseasons.api.SSItems;

public class SereneSeasonsCalendarAccessory implements SlotCopyingAccessory {

    public static void init() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.equals(MultiVersionUtil.parse("sereneseasons:calendar"))) {
                MultiVersionUtil.registerAccessory(item, new SereneSeasonsCalendarAccessory());
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.equals(MultiVersionUtil.parse("sereneseasons:calendar"))) {
                AccessoriesRendererRegistry.registerNoRenderer(item);
            }
        });
    }

    @Override
    public String getPath() {
        return "add_charm_4";
    }

    @Override
    public String getSlot() {
        return "charm";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, SSItems.CALENDAR);
    }
}
