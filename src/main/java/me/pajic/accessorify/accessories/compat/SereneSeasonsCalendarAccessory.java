package me.pajic.accessorify.accessories.compat;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import sereneseasons.api.SSItems;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class SereneSeasonsCalendarAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "add_charm_4");

    public static void init() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.equals(ResourceLocation.parse("sereneseasons:calendar"))) {
                //? if <= 1.21.1
                AccessoriesAPI.registerAccessory(item, new SereneSeasonsCalendarAccessory());
                //? if > 1.21.1
                /*AccessoryRegistry.register(item, new SereneSeasonsCalendarAccessory());*/
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.equals(ResourceLocation.parse("sereneseasons:calendar"))) {
                AccessoriesRendererRegistry.registerNoRenderer(item);
            }
        });
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
        return !reference.capability().isAnotherEquipped(stack, reference, SSItems.CALENDAR);
    }
}
