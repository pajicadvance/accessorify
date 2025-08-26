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

public class TotemOfIllusionAccessory implements SlotCopyingAccessory {

    @SubscribeEvent
    public static void init(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(MultiVersionUtil.parse("friendsandfoes:totem_of_illusion"))) {
                MultiVersionUtil.registerAccessory(value, new TotemOfIllusionAccessory());
            }
        });
    }

    @SubscribeEvent
    public static void clientInit(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(MultiVersionUtil.parse("friendsandfoes:totem_of_illusion"))) {
                MultiVersionUtil.noRenderer(value);
            }
        });
    }

    @Override
    public String getPath() {
        return "add_charm_2";
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