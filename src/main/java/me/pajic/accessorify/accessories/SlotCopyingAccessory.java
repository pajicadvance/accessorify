package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.config.SlotMode;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public interface SlotCopyingAccessory extends Accessory {
    String getPath();
    String getSlot();

    @Override
    default void onEquip(ItemStack stack, SlotReference reference) {
        if (Main.CONFIG.slotMode.get() == SlotMode.DEFAULT_SLOT) {
            var map = HashMultimap.<String, AttributeModifier>create();
            MultiVersionUtil.putAddAttributeModifier(map, getSlot(), getPath());
            reference.capability().addPersistentSlotModifiers(map);
        }
    }

    @Override
    default void onUnequip(ItemStack stack, SlotReference reference) {
        if (Main.CONFIG.slotMode.get() == SlotMode.DEFAULT_SLOT) {
            var map = HashMultimap.<String, AttributeModifier>create();
            MultiVersionUtil.putAddAttributeModifier(map, getSlot(), getPath());
            reference.capability().removeSlotModifiers(map);
        }
    }
}
