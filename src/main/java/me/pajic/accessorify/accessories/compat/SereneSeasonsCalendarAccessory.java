package me.pajic.accessorify.accessories.compat;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;
import sereneseasons.api.SSItems;

public class SereneSeasonsCalendarAccessory implements SlotCopyingAccessory {
    @SubscribeEvent
    public static void init(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(MultiVersionUtil.parse("sereneseasons:calendar"))) {
                MultiVersionUtil.registerAccessory(value, new SereneSeasonsCalendarAccessory());
            }
        });
    }

    @SubscribeEvent
    public static void clientInit(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(MultiVersionUtil.parse("sereneseasons:calendar"))) {
                AccessoriesRendererRegistry.registerNoRenderer(value);
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
