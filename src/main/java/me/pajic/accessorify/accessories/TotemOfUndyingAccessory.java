package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class TotemOfUndyingAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("accessorify", "add_charm");

    public static void init() {
        //? if <= 1.21.1
        AccessoriesAPI.registerAccessory(Items.TOTEM_OF_UNDYING, new TotemOfUndyingAccessory());
        //? if > 1.21.1
        /*AccessoryRegistry.register(Items.TOTEM_OF_UNDYING, new TotemOfUndyingAccessory());*/
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("charm", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().addPersistentSlotModifiers(map);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("charm", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().removeSlotModifiers(map);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !reference.capability().isAnotherEquipped(stack, reference, ModUtil::isTotem);
    }
}
