package me.pajic.accessorify.accessories.compat;

import com.google.common.collect.HashMultimap;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class FabricSeasonsCalendarAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "add_charm_3");

    public static void init() {
        //? if <= 1.21.1
        AccessoriesAPI.registerAccessory(FabricSeasonsExtras.SEASON_CALENDAR_ITEM, new FabricSeasonsCalendarAccessory());
        //? if > 1.21.1
        /*AccessoryRegistry.register(FabricSeasonsExtras.SEASON_CALENDAR_ITEM, new FabricSeasonsCalendarAccessory());*/
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        AccessoriesRendererRegistry.registerNoRenderer(FabricSeasonsExtras.SEASON_CALENDAR_ITEM);
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
        return !reference.capability().isAnotherEquipped(stack, reference, FabricSeasonsExtras.SEASON_CALENDAR_ITEM);
    }
}
