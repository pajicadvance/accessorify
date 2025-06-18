package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.Accessory;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;

public class ShulkerBoxAccessory implements Accessory {

    public static void init() {
        ModUtil.SHULKER_BOXES.forEach(item -> MultiVersionUtil.registerAccessory(item, new ShulkerBoxAccessory()));
    }
}
