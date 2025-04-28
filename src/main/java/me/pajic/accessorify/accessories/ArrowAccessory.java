package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.Accessory;
import me.pajic.accessorify.util.ModUtil;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class ArrowAccessory implements Accessory {

    public static void init() {
        //? if <= 1.21.1
        ModUtil.ARROWS.forEach(item -> AccessoriesAPI.registerAccessory(item, new ArrowAccessory()));
        //? if > 1.21.1
        /*ModUtil.ARROWS.forEach(item -> AccessoryRegistry.register(item, new ArrowAccessory()));*/
    }
}
