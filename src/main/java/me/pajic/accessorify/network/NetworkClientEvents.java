package me.pajic.accessorify.network;

import me.fzzyhmstrs.fzzy_config.networking.api.ClientPlayNetworkContext;
import me.pajic.accessorify.keybind.ModScrollHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class NetworkClientEvents {
    //? if >= 1.21.1 {
    public static void handleSyncShulkerSlotToClientPayload(Payloads.S2CSyncShulkerSlot payload, ClientPlayNetworking.Context context) {
        syncShulkerSlotToClient(payload.slot());
    }
    public static void handleSyncArrowSlotToClientPayload(Payloads.S2CSyncArrowSlot payload, ClientPlayNetworking.Context context) {
        syncArrowSlotToClient(payload.slot());
    }
    //?}
    //? if 1.20.1 {
    /*public static void handleSyncShulkerSlotToClientPayload(Payloads.S2CSyncShulkerSlot payload, ClientPlayNetworkContext context) {
        syncShulkerSlotToClient(payload.slot());
    }
    public static void handleSyncArrowSlotToClientPayload(Payloads.S2CSyncArrowSlot payload, ClientPlayNetworkContext context) {
        syncArrowSlotToClient(payload.slot());
    }
    *///?}

    private static void syncShulkerSlotToClient(int slot) {
        ModScrollHandler.selectedShulkerSlot = slot;
    }

    private static void syncArrowSlotToClient(int slot) {
        ModScrollHandler.selectedArrowSlot = slot;
    }
}
