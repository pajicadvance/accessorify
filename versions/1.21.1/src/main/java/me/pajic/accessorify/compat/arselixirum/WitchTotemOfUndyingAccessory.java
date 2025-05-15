package me.pajic.accessorify.compat.arselixirum;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class WitchTotemOfUndyingAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "add_charm_5");

    @SubscribeEvent
    public static void init(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(ResourceLocation.parse("elixirum:witch_totem_of_undying"))) {
                //? if <= 1.21.1
                AccessoriesAPI.registerAccessory(value, new WitchTotemOfUndyingAccessory());
                //? if > 1.21.1
                /*AccessoryRegistry.register(value, new WitchTotemOfUndyingAccessory());*/
            }
        });
    }

    @SubscribeEvent
    public static void clientInit(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(ResourceLocation.parse("elixirum:witch_totem_of_undying"))) {
                AccessoriesRendererRegistry.registerNoRenderer(value);
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
        return !reference.capability().isAnotherEquipped(stack, reference, ModUtil::isTotem);
    }
}
