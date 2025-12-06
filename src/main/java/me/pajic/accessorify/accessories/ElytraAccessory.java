package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.world.item.ItemStack;
//? if 1.21.1 {
/*import me.pajic.accessorify.util.CompatFlags;
import me.pajic.accessorify.util.compat.AileronCompat;
import net.minecraft.core.registries.Registries;
*///?}
//? if > 1.20.1
import net.minecraft.core.component.DataComponents;
//? if < 1.21.10
//import net.minecraft.world.item.ElytraItem;

public class ElytraAccessory implements AccessoryExtension {

    @Override
    public String getAttributePath() {
        return "add_cape";
    }

    @Override
    public String getDefaultSlot() {
        return "cape";
    }

    @Override
    public void getDynamicModifiers(ItemStack stack, SlotReference reference, AccessoryAttributeBuilder builder) {
        //? if 1.21.1
        //if (CompatFlags.AILERON_LOADED) AileronCompat.addModifiers(builder, stack, reference.entity().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference,
                //? if <= 1.21.1
                //itemStack -> itemStack.getItem() instanceof ElytraItem
                //? if > 1.21.1
                itemStack -> itemStack.has(DataComponents.GLIDER)
        );
    }

	@Override
	public boolean canEquipFromUse(ItemStack stack, SlotReference reference) {
		return !Accessorify.CONFIG.preventElytraRightClickEquip.get() && AccessoryExtension.super.canEquipFromUse(stack, reference);
	}
}
