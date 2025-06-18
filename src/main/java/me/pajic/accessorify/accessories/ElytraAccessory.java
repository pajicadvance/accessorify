package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if <= 1.21.1
import net.minecraft.world.item.ElytraItem;
//? if > 1.21.1
/*import net.minecraft.core.component.DataComponents;*/

public class ElytraAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.ELYTRA, new ElytraAccessory());
    }

    @Override
    public String getPath() {
        return "add_cape";
    }

    @Override
    public String getSlot() {
        return "cape";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference,
                //? if <= 1.21.1
                itemStack -> itemStack.getItem() instanceof ElytraItem
                //? if > 1.21.1
                /*itemStack -> itemStack.has(DataComponents.GLIDER)*/
        );
    }
}
