package me.pajic.accessorify.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.menu.ShulkerBoxAccessoryContainerMenu;
import me.pajic.accessorify.util.PlayerExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
//? if >= 1.21.1 && fabric
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//? if < 1.21.1
//import me.fzzyhmstrs.fzzy_config.networking.api.ServerPlayNetworkContext;

import java.util.Optional;

public class NetworkEvents {
	//? if fabric {
    //? if >= 1.21.1 {
    public static void handleOpenShulkerBoxPayload(Payloads.C2SOpenShulkerBoxPayload payload, ServerPlayNetworking.Context context) {
        openShulkerBox(context.player(), payload.index());
    }
    public static void handleOpenEnderContainerPayload(Payloads.C2SOpenEnderContainerPayload payload, ServerPlayNetworking.Context context) {
        openEnderContainer(context.player());
    }
    public static void handleSyncShulkerSlotToServerPayload(Payloads.C2SSyncShulkerSlot payload, ServerPlayNetworking.Context context) {
        syncShulkerSlotToServer(context.player(), payload.slot());
    }
    public static void handleSyncArrowSlotToServerPayload(Payloads.C2SSyncArrowSlot payload, ServerPlayNetworking.Context context) {
        syncArrowSlotToServer(context.player(), payload.slot());
    }
    //?} else {
    /*public static void handleOpenShulkerBoxPayload(Payloads.C2SOpenShulkerBoxPayload payload, ServerPlayNetworkContext context) {
        openShulkerBox(context.player(), payload.index());
    }
    public static void handleOpenEnderContainerPayload(Payloads.C2SOpenEnderContainerPayload payload, ServerPlayNetworkContext context) {
        openEnderContainer(context.player());
    }
    public static void handleSyncShulkerSlotToServerPayload(Payloads.C2SSyncShulkerSlot payload, ServerPlayNetworkContext context) {
        syncShulkerSlotToServer(context.player(), payload.slot());
    }
    public static void handleSyncArrowSlotToServerPayload(Payloads.C2SSyncArrowSlot payload, ServerPlayNetworkContext context) {
        syncArrowSlotToServer(context.player(), payload.slot());
    }
    *///?}
	//?}

    public static void openShulkerBox(ServerPlayer player, int index) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent()) {
            ItemStack shulker = ac.get().getContainers().get("shulker").getAccessories().getItem(index);
            player.openMenu(new ShulkerBoxAccessoryContainerMenu(shulker, 27));
            player.awardStat(Stats.OPEN_SHULKER_BOX);
        }
    }

	public static void openEnderContainer(ServerPlayer player) {
        player.playSound(SoundEvents.ENDER_CHEST_OPEN);
        PlayerEnderChestContainer container = player.getEnderChestInventory();
        player.openMenu(new SimpleMenuProvider((i, inventory, player1) ->
                ChestMenu.threeRows(i, inventory, container), Component.translatable("container.enderchest")
        ));
        player.awardStat(Stats.OPEN_ENDERCHEST);
    }

	public static void syncShulkerSlotToServer(ServerPlayer player, int slot) {
        ((PlayerExtension) player).accessorify$setShulkerSlot(slot);
    }

	public static void syncArrowSlotToServer(ServerPlayer player, int slot) {
        ((PlayerExtension) player).accessorify$setArrowSlot(slot);
    }
}
