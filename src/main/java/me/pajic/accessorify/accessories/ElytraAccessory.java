package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;
//? if <= 1.21.1 {
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.world.item.ElytraItem;
//?}
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class ElytraAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("accessorify", "add_cape");

    public static void init() {
        //? if <= 1.21.1
        AccessoriesAPI.registerAccessory(Items.ELYTRA, new ElytraAccessory());
        //? if > 1.21.1
        /*AccessoryRegistry.register(Items.ELYTRA, new ElytraAccessory());*/
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("cape", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().addPersistentSlotModifiers(map);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("cape", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().removeSlotModifiers(map);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !reference.capability().isAnotherEquipped(stack, reference,
                //? if <= 1.21.1
                itemStack -> itemStack.getItem() instanceof ElytraItem
                //? if > 1.21.1
                /*itemStack -> itemStack.has(DataComponents.GLIDER)*/
        );
    }
}
