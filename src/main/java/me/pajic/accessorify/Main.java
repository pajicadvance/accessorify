package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.datapacks.ModDatapacks;
import net.fabricmc.api.ModInitializer;
import me.pajic.accessorify.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();
    public static final boolean DEEPER_DARKER_LOADED = FabricLoader.getInstance().isModLoaded("deeperdarker");

    @Override
    public void onInitialize() {
        ModDatapacks.init();
        if (CONFIG.clockAccessory()) ClockAccessory.init();
        if (CONFIG.compassAccessory()) CompassAccessory.init();
        if (CONFIG.elytraAccessory()) {
            ElytraAccessory.init();
            if (DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.init();
            }
        }
        if (CONFIG.spyglassAccessory()) SpyglassAccessory.init();
        if (CONFIG.totemOfUndyingAccessory()) TotemOfUndyingAccessory.init();
    }
}
