package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//? if <= 1.21.1
import io.wispforest.accessories.api.AccessoriesAPI;
//? if > 1.21.1
/*import io.wispforest.accessories.api.AccessoryRegistry;*/

public class ShulkerBoxAccessory implements Accessory {

    public static void init() {
        //? if <= 1.21.1
        ModUtil.SHULKER_BOXES.forEach(item -> AccessoriesAPI.registerAccessory(item, new ShulkerBoxAccessory()));
        //? if > 1.21.1
        /*ModUtil.SHULKER_BOXES.forEach(item -> AccessoryRegistry.register(item, new ShulkerBoxAccessory()));*/
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        ModUtil.SHULKER_BOXES.forEach(AccessoriesRendererRegistry::registerNoRenderer);
    }
}
