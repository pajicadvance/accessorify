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
        ClockAccessory.clientInit();
        CompassAccessory.clientInit();
        ElytraAccessory.clientInit();
        ModKeybinds.initKeybinds();
        InfoOverlays.initOverlay();
    }
}
