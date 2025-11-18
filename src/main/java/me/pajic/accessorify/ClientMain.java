package me.pajic.accessorify;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.*;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;
//? if 1.21.1
/*import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;*/
//? if <= 1.21.1 {
/*import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.compat.netheriteextras.TotemOfNeverdyingAccessory;
 *///?}

public class ClientMain implements ClientModInitializer {
    public static final ResourceLocation CLIENT_CONFIG_RL = MultiVersionUtil.withModNamespace("client_config");
    public static ModClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);

    @Override
    public void onInitializeClient() {
        if (Main.CONFIG.accessorySettings.clockAccessory.get()) ClockAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.compassAccessory.get()) CompassAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            ElytraAccessory.clientInit();
            //? if <= 1.21.1 {
            /*if (CompatFlags.DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.clientInit();
            }
            if (CompatFlags.NETHERITE_EXTRAS_LOADED) {
                TotemOfNeverdyingAccessory.clientInit();
            }
            *///?}
        }
        if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get()) RecoveryCompassAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.spyglassAccessory.get()) SpyglassAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.lanternAccessory.get()) LanternAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
            TotemOfUndyingAccessory.clientInit();
            if (CompatFlags.FRIENDS_AND_FOES_LOADED) {
                TotemOfFreezingAccessory.clientInit();
                TotemOfIllusionAccessory.clientInit();
            }
            //? if 1.21.1 {
            /*if (CompatFlags.ARS_ELIXIRUM_LOADED) {
                WitchTotemOfUndyingAccessory.clientInit();
            }
            *///?}
        }
        if (Main.CONFIG.accessorySettings.enderChestAccessory.get()) EnderChestAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get()) ShulkerBoxAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.arrowAccessory.get()) ArrowAccessory.clientInit();
        if (Main.CONFIG.accessorySettings.calendarAccessory.get()) {
            if (CompatFlags.FABRIC_SEASONS_LOADED && CompatFlags.FABRIC_SEASONS_EXTRAS_LOADED) FabricSeasonsCalendarAccessory.clientInit();
            else if (CompatFlags.SERENE_SEASONS_LOADED) SereneSeasonsCalendarAccessory.clientInit();
        }
        ModKeybinds.initKeybinds();
        //? if >= 1.21.1
        ModNetworking.initClient();
    }
}
