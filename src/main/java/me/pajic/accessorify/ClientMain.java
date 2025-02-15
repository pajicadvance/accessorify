package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;
//? if <= 1.21.1
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;

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
        if (Main.CONFIG.shulkerBoxAccessory()) ShulkerBoxAccessory.clientInit();
        ModKeybinds.initKeybinds();
        InfoOverlays.initOverlay();
    }
}
