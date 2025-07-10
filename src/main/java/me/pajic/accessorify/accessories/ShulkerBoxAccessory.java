package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxAccessory implements Accessory {

    public static void init() {
        ModUtil.SHULKER_BOXES.forEach(item -> MultiVersionUtil.registerAccessory(item, new ShulkerBoxAccessory()));
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        ModUtil.SHULKER_BOXES.forEach(AccessoriesRendererRegistry::registerNoRenderer);
    }

    @Override
    public int maxStackSize(ItemStack stack){
        return 1;
    }
}
