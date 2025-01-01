package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class SpyglassAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("accessorify", "add_belt");

    public static void init() {
        //? if <= 1.21.1
        AccessoriesAPI.registerAccessory(Items.SPYGLASS, new SpyglassAccessory());
        //? if > 1.21.1
        /*AccessoryRegistry.register(Items.SPYGLASS, new SpyglassAccessory());*/
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("belt", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().addPersistentSlotModifiers(map);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("belt", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().removeSlotModifiers(map);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !reference.capability().isAnotherEquipped(stack, reference, Items.SPYGLASS);
    }
}
