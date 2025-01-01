package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.datapacks.ModDatapacks;
import net.fabricmc.api.ModInitializer;
import me.pajic.accessorify.config.ModConfig;

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();

    @Override
    public void onInitialize() {
        ModDatapacks.init();
        if (CONFIG.clockAccessory()) ClockAccessory.init();
        if (CONFIG.compassAccessory()) CompassAccessory.init();
        if (CONFIG.elytraAccessory()) ElytraAccessory.init();
        if (CONFIG.spyglassAccessory()) SpyglassAccessory.init();
        if (CONFIG.totemOfUndyingAccessory()) TotemOfUndyingAccessory.init();
    }
}
