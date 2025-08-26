package me.pajic.accessorify.accessories;

//$ Accessory
import io.wispforest.accessories.api.Accessory;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxAccessory implements Accessory {

    public static void init() {
        ModUtil.SHULKER_BOXES.forEach(item -> MultiVersionUtil.registerAccessory(item, new ShulkerBoxAccessory()));
    }

    @Override
    public int maxStackSize(ItemStack stack) {
        return 1;
    }
}
