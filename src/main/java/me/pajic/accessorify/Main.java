package me.pajic.accessorify;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.*;
import me.pajic.accessorify.config.ModConfig;
import me.pajic.accessorify.datapacks.ModDatapacks;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
//? if 1.21.1
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
//?}

public class Main implements ModInitializer {
    public static final String MOD_ID = "accessorify";
    public static final ResourceLocation CONFIG_RL = MultiVersionUtil.withModNamespace("config");
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

    @Override
    public void onInitialize() {
        ModDatapacks.init();
        if (CONFIG.accessorySettings.clockAccessory.get()) ClockAccessory.init();
        if (CONFIG.accessorySettings.compassAccessory.get()) CompassAccessory.init();
        if (CONFIG.accessorySettings.elytraAccessory.get()) {
            ElytraAccessory.init();
            //? if <= 1.21.1 {
            if (CompatFlags.DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.init();
            }
            //?}
        }
        if (CONFIG.accessorySettings.spyglassAccessory.get()) SpyglassAccessory.init();
        if (CONFIG.accessorySettings.lanternAccessory.get()) {
            LanternAccessory.init();
            SoulLanternAccessory.init();
            if (CompatFlags.ADDITIONAL_LANTERNS_LOADED) {
                AdditionalLanternAccessory.init();
            }
        }
        if (CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
            TotemOfUndyingAccessory.init();
            //? if <= 1.21.1 {
            if (CompatFlags.FRIENDS_AND_FOES_LOADED) {
                TotemOfFreezingAccessory.init();
                TotemOfIllusionAccessory.init();
            }
            //?}
            //? if 1.21.1 {
            if (CompatFlags.ARS_ELIXIRUM_LOADED) {
                WitchTotemOfUndyingAccessory.init();
            }
            //?}
        }
        if (CONFIG.accessorySettings.recoveryCompassAccessory.get()) RecoveryCompassAccessory.init();
        if (CONFIG.accessorySettings.enderChestAccessory.get()) EnderChestAccessory.init();
        if (CONFIG.accessorySettings.shulkerBoxAccessory.get()) ShulkerBoxAccessory.init();
        if (CONFIG.accessorySettings.arrowAccessory.get()) ArrowAccessory.init();
        if (CONFIG.accessorySettings.calendarAccessory.get()) {
            if (CompatFlags.FABRIC_SEASONS_LOADED && CompatFlags.FABRIC_SEASONS_EXTRAS_LOADED) FabricSeasonsCalendarAccessory.init();
            else if (CompatFlags.SERENE_SEASONS_LOADED) SereneSeasonsCalendarAccessory.init();
        }
        ModNetworking.init();
    }
}
