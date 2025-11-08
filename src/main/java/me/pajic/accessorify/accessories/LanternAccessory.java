package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

public class LanternAccessory implements SlotCopyingAccessory{

    @SubscribeEvent
    public static void init(TagsUpdatedEvent event) {
        //? if > 1.21.1 {
        /*event.getLookupProvider()
                .lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.LANTERNS).forEach(itemHolder ->
                        MultiVersionUtil.registerAccessory(itemHolder.value(), new LanternAccessory())
                );
        *///?}
        //? if <= 1.21.1
        MultiVersionUtil.registerAccessory(Items.LANTERN, new LanternAccessory());
    }

    @Override
    public String getPath() {
        return "add_belt_1";
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
