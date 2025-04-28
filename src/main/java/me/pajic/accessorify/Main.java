package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.FabricSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.datapacks.ModDatapacks;
import me.pajic.accessorify.network.ModNetworking;
import net.fabricmc.api.ModInitializer;
import me.pajic.accessorify.config.ModConfig;
import net.fabricmc.loader.api.FabricLoader;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfFreezingAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfIllusionAccessory;
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
//?}

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();
    public static final String MOD_ID = "accessorify";
    public static final boolean DEEPER_DARKER_LOADED = FabricLoader.getInstance().isModLoaded("deeperdarker");
    public static final boolean FRIENDS_AND_FOES_LOADED = FabricLoader.getInstance().isModLoaded("friendsandfoes");
    public static final boolean ELERON_LOADED = FabricLoader.getInstance().isModLoaded("eleron");
    public static final boolean RAISED_LOADED = FabricLoader.getInstance().isModLoaded("raised");
    public static final boolean SERENE_SEASONS_LOADED = FabricLoader.getInstance().isModLoaded("sereneseasons");
    public static final boolean FABRIC_SEASONS_LOADED = FabricLoader.getInstance().isModLoaded("seasons");
    public static final boolean FABRIC_SEASONS_EXTRAS_LOADED = FabricLoader.getInstance().isModLoaded("seasonsextras");
    public static final boolean ARS_ELIXIRUM_LOADED = FabricLoader.getInstance().isModLoaded("elixirum");

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
            if (ARS_ELIXIRUM_LOADED) {
                WitchTotemOfUndyingAccessory.init();
            }
            //?}
        }
        if (CONFIG.recoveryCompassAccessory()) RecoveryCompassAccessory.init();
        if (CONFIG.enderChestAccessory()) EnderChestAccessory.init();
        if (CONFIG.shulkerBoxAccessory()) ShulkerBoxAccessory.init();
        if (CONFIG.arrowAccessory()) ArrowAccessory.init();
        if (CONFIG.calendarAccessory()) {
            if (FABRIC_SEASONS_LOADED && FABRIC_SEASONS_EXTRAS_LOADED) FabricSeasonsCalendarAccessory.init();
            else if (SERENE_SEASONS_LOADED) SereneSeasonsCalendarAccessory.init();
        }
        ModNetworking.init();
    }
}
