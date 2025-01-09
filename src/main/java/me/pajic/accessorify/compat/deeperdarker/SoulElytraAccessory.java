package me.pajic.accessorify.compat.deeperdarker;

import com.google.common.collect.HashMultimap;
import com.kyanite.deeperdarker.content.DDItems;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
//? if <= 1.21.1 {
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.world.item.ElytraItem;
//?}
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class SoulElytraAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("accessorify", "add_cape_1");

    public static void init() {
        //? if <= 1.21.1
        AccessoriesAPI.registerAccessory(DDItems.SOUL_ELYTRA, new SoulElytraAccessory());
        //? if > 1.21.1
        /*AccessoryRegistry.register(DDItems.SOUL_ELYTRA, new SoulElytraAccessory());*/
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        AccessoriesRendererRegistry.registerNoRenderer(DDItems.SOUL_ELYTRA);
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
