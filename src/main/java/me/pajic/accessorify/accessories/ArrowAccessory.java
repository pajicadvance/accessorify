package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ArrowAccessory implements Accessory {

    public static void init() {
        ModUtil.ARROWS.forEach(item -> MultiVersionUtil.registerAccessory(item, new ArrowAccessory()));
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        ModUtil.ARROWS.forEach(AccessoriesRendererRegistry::registerNoRenderer);
    }
}
