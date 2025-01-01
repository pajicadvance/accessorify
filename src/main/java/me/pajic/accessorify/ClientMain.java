package me.pajic.accessorify;

import me.pajic.accessorify.accessories.ClockAccessory;
import me.pajic.accessorify.accessories.CompassAccessory;
import me.pajic.accessorify.accessories.ElytraAccessory;
import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;

public class ClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (Main.CONFIG.clockAccessory()) ClockAccessory.clientInit();
        if (Main.CONFIG.compassAccessory()) CompassAccessory.clientInit();
        if (Main.CONFIG.elytraAccessory()) ElytraAccessory.clientInit();
        if (Main.CONFIG.spyglassAccessory()) ModKeybinds.initKeybinds();
        if (Main.CONFIG.clockAccessory() || Main.CONFIG.compassAccessory()) InfoOverlays.initOverlay();
    }
}
