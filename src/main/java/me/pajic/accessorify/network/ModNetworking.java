package me.pajic.accessorify.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.access.SelectedAccessorySlotAccess;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.menu.ShulkerBoxAccessoryContainerMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModNetworking {

    public static final ResourceLocation OPEN_SHULKER_BOX = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "open_shulker_box");
    public static final ResourceLocation OPEN_ENDER_CONTAINER = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "open_ender_container");
    public static final ResourceLocation C2S_SYNC_SHULKER_SLOT = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "c2s_sync_shulker_slot");
    public static final ResourceLocation S2C_SYNC_SHULKER_SLOT = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "s2c_sync_shulker_slot");
    public static final ResourceLocation C2S_SYNC_ARROW_SLOT = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "c2s_sync_arrow_slot");
    public static final ResourceLocation S2C_SYNC_ARROW_SLOT = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "s2c_sync_arrow_slot");

    public record C2SOpenShulkerBoxPayload(int index) implements CustomPacketPayload {
        public static final Type<C2SOpenShulkerBoxPayload> TYPE = new Type<>(OPEN_SHULKER_BOX);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SOpenShulkerBoxPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, C2SOpenShulkerBoxPayload::index,
                C2SOpenShulkerBoxPayload::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record C2SOpenEnderContainerPayload() implements CustomPacketPayload {
        public static final Type<C2SOpenEnderContainerPayload> TYPE = new Type<>(OPEN_ENDER_CONTAINER);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SOpenEnderContainerPayload> CODEC = StreamCodec.unit(
                new C2SOpenEnderContainerPayload()
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record C2SSyncShulkerSlot(int slot) implements CustomPacketPayload {
        public static final Type<C2SSyncShulkerSlot> TYPE = new Type<>(C2S_SYNC_SHULKER_SLOT);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SSyncShulkerSlot> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, C2SSyncShulkerSlot::slot,
                C2SSyncShulkerSlot::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record S2CSyncShulkerSlot(int slot) implements CustomPacketPayload {
        public static final Type<S2CSyncShulkerSlot> TYPE = new Type<>(S2C_SYNC_SHULKER_SLOT);
        public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncShulkerSlot> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, S2CSyncShulkerSlot::slot,
                S2CSyncShulkerSlot::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record C2SSyncArrowSlot(int slot) implements CustomPacketPayload {
        public static final Type<C2SSyncArrowSlot> TYPE = new Type<>(C2S_SYNC_ARROW_SLOT);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SSyncArrowSlot> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, C2SSyncArrowSlot::slot,
                C2SSyncArrowSlot::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record S2CSyncArrowSlot(int slot) implements CustomPacketPayload {
        public static final Type<S2CSyncArrowSlot> TYPE = new Type<>(S2C_SYNC_ARROW_SLOT);
        public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncArrowSlot> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, S2CSyncArrowSlot::slot,
                S2CSyncArrowSlot::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void init() {
        PayloadTypeRegistry.playC2S().register(C2SOpenShulkerBoxPayload.TYPE, C2SOpenShulkerBoxPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(C2SOpenEnderContainerPayload.TYPE, C2SOpenEnderContainerPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(C2SSyncShulkerSlot.TYPE, C2SSyncShulkerSlot.CODEC);
        PayloadTypeRegistry.playS2C().register(S2CSyncShulkerSlot.TYPE, S2CSyncShulkerSlot.CODEC);
        PayloadTypeRegistry.playC2S().register(C2SSyncArrowSlot.TYPE, C2SSyncArrowSlot.CODEC);
        PayloadTypeRegistry.playS2C().register(S2CSyncArrowSlot.TYPE, S2CSyncArrowSlot.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(C2SOpenShulkerBoxPayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                player.openMenu(new ShulkerBoxAccessoryContainerMenu(ac.get().getContainers().get("shulker").getAccessories().getItem(payload.index)));
                player.awardStat(Stats.OPEN_SHULKER_BOX);
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SOpenEnderContainerPayload.TYPE, (payload, context) -> {
            context.player().playSound(SoundEvents.ENDER_CHEST_OPEN);
            PlayerEnderChestContainer container = context.player().getEnderChestInventory();
            context.player().openMenu(new SimpleMenuProvider((i, inventory, player1) ->
                    ChestMenu.threeRows(i, inventory, container), Component.translatable("container.enderchest")
            ));
            context.player().awardStat(Stats.OPEN_ENDERCHEST);
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SSyncShulkerSlot.TYPE, (payload, context) ->
                ((SelectedAccessorySlotAccess) context.player()).accessorify$setShulkerSlot(payload.slot));

        ServerPlayNetworking.registerGlobalReceiver(C2SSyncArrowSlot.TYPE, (payload, context) ->
                ((SelectedAccessorySlotAccess) context.player()).accessorify$setArrowSlot(payload.slot)
        );
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(S2CSyncShulkerSlot.TYPE, (payload, context) ->
                ModScrollHandler.selectedShulkerSlot = payload.slot);

        ClientPlayNetworking.registerGlobalReceiver(S2CSyncArrowSlot.TYPE, (payload, context) ->
                ModScrollHandler.selectedArrowSlot = payload.slot);
    }
}
