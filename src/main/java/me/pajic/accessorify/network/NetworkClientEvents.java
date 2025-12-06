package me.pajic.accessorify.network;

import me.pajic.accessorify.keybind.ModScrollHandler;
//? if fabric
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//? if < 1.21.1
//import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;

public class NetworkClientEvents {
	//? if fabric {
    //? if >= 1.21.1 {
    public static void handleSyncShulkerSlotToClientPayload(Payloads.S2CSyncShulkerSlot payload, ClientPlayNetworking.Context context) {
        syncShulkerSlotToClient(payload.slot());
    }
    public static void handleSyncArrowSlotToClientPayload(Payloads.S2CSyncArrowSlot payload, ClientPlayNetworking.Context context) {
        syncArrowSlotToClient(payload.slot());
    }
    //?} else {
    /*public static void handleSyncShulkerSlotToClientPayload(Payloads.S2CSyncShulkerSlot payload, ClientPlayNetworkContext context) {
        syncShulkerSlotToClient(payload.slot());
    }
    public static void handleSyncArrowSlotToClientPayload(Payloads.S2CSyncArrowSlot payload, ClientPlayNetworkContext context) {
        syncArrowSlotToClient(payload.slot());
    }
    *///?}
	//?}

	public static void syncShulkerSlotToClient(int slot) {
        ModScrollHandler.selectedShulkerSlot = slot;
    }

	public static void syncArrowSlotToClient(int slot) {
        ModScrollHandler.selectedArrowSlot = slot;
    }
}
