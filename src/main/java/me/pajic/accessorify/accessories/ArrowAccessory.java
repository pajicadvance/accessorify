package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.Accessory;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;

public class ArrowAccessory implements Accessory {

    public static void init() {
        ModUtil.ARROWS.forEach(item -> MultiVersionUtil.registerAccessory(item, new ArrowAccessory()));
    }
}
