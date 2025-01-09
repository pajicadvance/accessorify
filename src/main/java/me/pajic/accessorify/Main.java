package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.datapacks.ModDatapacks;
import net.fabricmc.api.ModInitializer;
import me.pajic.accessorify.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.compat.friendsandfoes.TotemOfFreezingAccessory;
import me.pajic.accessorify.compat.friendsandfoes.TotemOfIllusionAccessory;
//?}

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();
    //? if <= 1.21.1 {
    public static final boolean DEEPER_DARKER_LOADED = FabricLoader.getInstance().isModLoaded("deeperdarker");
    public static final boolean FRIENDS_AND_FOES_LOADED = FabricLoader.getInstance().isModLoaded("friendsandfoes");
    //?}

    @Override
    public void onInitialize() {
        ModDatapacks.init();
        if (CONFIG.clockAccessory()) ClockAccessory.init();
        if (CONFIG.compassAccessory()) CompassAccessory.init();
        if (CONFIG.elytraAccessory()) {
            ElytraAccessory.init();
            //? if <= 1.21.1 {
            if (DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.init();
            }
            //?}
        }
        if (CONFIG.spyglassAccessory()) SpyglassAccessory.init();
        if (CONFIG.totemOfUndyingAccessory()) {
            TotemOfUndyingAccessory.init();
            //? if <= 1.21.1 {
            if (FRIENDS_AND_FOES_LOADED) {
                TotemOfFreezingAccessory.init();
                TotemOfIllusionAccessory.init();
            }
            //?}
        }
    }
}
