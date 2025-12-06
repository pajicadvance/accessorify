package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.config.SlotMode;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("ConstantConditions")
public interface AccessoryExtension extends Accessory {
    String getAttributePath();
    String getDefaultSlot();

    @Override
    default void onEquip(ItemStack stack, SlotReference reference) {
        if (Accessorify.CONFIG.slotMode.get() == SlotMode.DEFAULT_SLOT) {
            var map = HashMultimap.<String, AttributeModifier>create();
            AccessoryUtil.putAddAttributeModifier(map, getDefaultSlot(), getAttributePath());
            reference.capability().addPersistentSlotModifiers(map);
        }
    }

    @Override
    default void onUnequip(ItemStack stack, SlotReference reference) {
        if (Accessorify.CONFIG.slotMode.get() == SlotMode.DEFAULT_SLOT) {
            var map = HashMultimap.<String, AttributeModifier>create();
            AccessoryUtil.putAddAttributeModifier(map, getDefaultSlot(), getAttributePath());
            reference.capability().removeSlotModifiers(map);
        }
    }
}
