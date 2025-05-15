package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.FabricSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfFreezingAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfIllusionAccessory;
import me.pajic.accessorify.gui.ArrowSelectionWidget;
import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.gui.ShulkerBoxSelectionWidget;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.network.ModNetworking;
import net.fabricmc.api.ClientModInitializer;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
//?}

public class ClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (Main.CONFIG.clockAccessory()) ClockAccessory.clientInit();
        if (Main.CONFIG.compassAccessory()) CompassAccessory.clientInit();
        if (Main.CONFIG.elytraAccessory()) {
            ElytraAccessory.clientInit();
            //? if <= 1.21.1 {
            if (Main.DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.clientInit();
            }
            //?}
        }
        if (Main.CONFIG.recoveryCompassAccessory()) RecoveryCompassAccessory.clientInit();
        if (Main.CONFIG.spyglassAccessory()) SpyglassAccessory.clientInit();
        if (Main.CONFIG.totemOfUndyingAccessory()) {
            TotemOfUndyingAccessory.clientInit();
            if (Main.FRIENDS_AND_FOES_LOADED) {
                TotemOfFreezingAccessory.clientInit();
                TotemOfIllusionAccessory.clientInit();
            }
            //? if <= 1.21.1 {
            if (Main.ARS_ELIXIRUM_LOADED) {
                WitchTotemOfUndyingAccessory.clientInit();
            }
            //?}
        }
        if (Main.CONFIG.enderChestAccessory()) EnderChestAccessory.clientInit();
        if (Main.CONFIG.shulkerBoxAccessory()) ShulkerBoxAccessory.clientInit();
        if (Main.CONFIG.arrowAccessory()) ArrowAccessory.clientInit();
        if (Main.CONFIG.calendarAccessory()) {
            if (Main.FABRIC_SEASONS_LOADED && Main.FABRIC_SEASONS_EXTRAS_LOADED) FabricSeasonsCalendarAccessory.clientInit();
            else if (Main.SERENE_SEASONS_LOADED) SereneSeasonsCalendarAccessory.clientInit();
        }
        ModKeybinds.initKeybinds();
        ModNetworking.initClient();
        InfoOverlays.initOverlay();
        ShulkerBoxSelectionWidget.initOverlay();
        ArrowSelectionWidget.initOverlay();
    }
}
