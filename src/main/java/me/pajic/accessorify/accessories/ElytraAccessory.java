package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import me.pajic.accessorify.compat.EleronCompat;
//? if <= 1.21.1 {
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.world.item.ElytraItem;
//? }
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

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        AccessoriesRendererRegistry.registerNoRenderer(Items.ELYTRA);
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
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        if (Main.ELERON_LOADED) EleronCompat.addModifiers(builder, stack, reference.entity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
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
