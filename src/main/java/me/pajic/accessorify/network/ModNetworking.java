package me.pajic.accessorify.network;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//? if >= 1.21.1
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModNetworking {
    //? if >= 1.21.1 {
    public static void init() {
        PayloadTypeRegistry.playC2S().register(Payloads.C2SOpenShulkerBoxPayload.TYPE, Payloads.C2SOpenShulkerBoxPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(Payloads.C2SOpenEnderContainerPayload.TYPE, Payloads.C2SOpenEnderContainerPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(Payloads.C2SSyncShulkerSlot.TYPE, Payloads.C2SSyncShulkerSlot.CODEC);
        PayloadTypeRegistry.playS2C().register(Payloads.S2CSyncShulkerSlot.TYPE, Payloads.S2CSyncShulkerSlot.CODEC);
        PayloadTypeRegistry.playC2S().register(Payloads.C2SSyncArrowSlot.TYPE, Payloads.C2SSyncArrowSlot.CODEC);
        PayloadTypeRegistry.playS2C().register(Payloads.S2CSyncArrowSlot.TYPE, Payloads.S2CSyncArrowSlot.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                Payloads.C2SOpenShulkerBoxPayload.TYPE,
                NetworkEvents::handleOpenShulkerBoxPayload
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Payloads.C2SOpenEnderContainerPayload.TYPE,
                NetworkEvents::handleOpenEnderContainerPayload
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Payloads.C2SSyncShulkerSlot.TYPE,
                NetworkEvents::handleSyncShulkerSlotToServerPayload
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Payloads.C2SSyncArrowSlot.TYPE,
                NetworkEvents::handleSyncArrowSlotToServerPayload
        );
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(
                Payloads.S2CSyncShulkerSlot.TYPE,
                NetworkClientEvents::handleSyncShulkerSlotToClientPayload
        );
        ClientPlayNetworking.registerGlobalReceiver(
                Payloads.S2CSyncArrowSlot.TYPE,
                NetworkClientEvents::handleSyncArrowSlotToClientPayload
        );
    }
    //?}
    //? if 1.20.1 {
    /*public static void init() {
        ConfigApiJava.network().registerLenientC2S(
                NetworkConstants.OPEN_SHULKER_BOX,
                Payloads.C2SOpenShulkerBoxPayload.class,
                Payloads.C2SOpenShulkerBoxPayload::new,
                NetworkEvents::handleOpenShulkerBoxPayload
        );
        ConfigApiJava.network().registerLenientC2S(
                NetworkConstants.OPEN_ENDER_CONTAINER,
                Payloads.C2SOpenEnderContainerPayload.class,
                Payloads.C2SOpenEnderContainerPayload::new,
                NetworkEvents::handleOpenEnderContainerPayload
        );
        ConfigApiJava.network().registerLenientC2S(
                NetworkConstants.C2S_SYNC_SHULKER_SLOT,
                Payloads.C2SSyncShulkerSlot.class,
                Payloads.C2SSyncShulkerSlot::new,
                NetworkEvents::handleSyncShulkerSlotToServerPayload
        );
        ConfigApiJava.network().registerLenientC2S(
                NetworkConstants.C2S_SYNC_ARROW_SLOT,
                Payloads.C2SSyncArrowSlot.class,
                Payloads.C2SSyncArrowSlot::new,
                NetworkEvents::handleSyncArrowSlotToServerPayload
        );
        ConfigApiJava.network().registerLenientS2C(
                NetworkConstants.S2C_SYNC_SHULKER_SLOT,
                Payloads.S2CSyncShulkerSlot.class,
                Payloads.S2CSyncShulkerSlot::new,
                NetworkClientEvents::handleSyncShulkerSlotToClientPayload
        );
        ConfigApiJava.network().registerLenientS2C(
                NetworkConstants.S2C_SYNC_ARROW_SLOT,
                Payloads.S2CSyncArrowSlot.class,
                Payloads.S2CSyncArrowSlot::new,
                NetworkClientEvents::handleSyncArrowSlotToClientPayload
        );
    }
    *///?}
}
