package me.pajic.accessorify.accessories;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class ShulkerBoxAccessory implements Accessory {

    private static final List<ResourceLocation> rls = new ArrayList<>();

    public static void init() {
        //? if <= 1.21.1
        ModUtil.SHULKER_BOXES.forEach(item -> AccessoriesAPI.registerAccessory(item, new ShulkerBoxAccessory()));
        //? if > 1.21.1
        /*ModUtil.SHULKER_BOXES.forEach(item -> AccessoryRegistry.register(item, new ShulkerBoxAccessory()));*/
        for (int i = 0; i < ModCommonConfig.allowedShulkerBoxes; i++) {
            rls.add(ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "add_back_" + i));
        }
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("back", new AttributeModifier(rls.get(reference.slot()), 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().addPersistentSlotModifiers(map);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("back", new AttributeModifier(rls.get(reference.slot()), 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().removeSlotModifiers(map);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return reference.capability().getEquipped(ModUtil::isShulkerBox).size() < ModCommonConfig.allowedShulkerBoxes;
    }
}
