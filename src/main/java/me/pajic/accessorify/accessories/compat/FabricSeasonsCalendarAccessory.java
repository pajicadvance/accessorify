package me.pajic.accessorify.accessories.compat;

import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class FabricSeasonsCalendarAccessory implements SlotCopyingAccessory {

    public static void init() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.equals(MultiVersionUtil.parse("seasonsextras:season_calendar"))) {
                MultiVersionUtil.registerAccessory(item, new FabricSeasonsCalendarAccessory());
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.equals(MultiVersionUtil.parse("seasonsextras:season_calendar"))) {
                MultiVersionUtil.noRenderer(item);
            }
        });
    }

    @Override
    public String getPath() {
        return "add_charm_3";
    }

    @Override
    public String getSlot() {
        return "charm";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, FabricSeasonsExtras.SEASON_CALENDAR_ITEM);
    }
}
