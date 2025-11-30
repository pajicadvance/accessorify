package me.pajic.accessorify.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.access.SelectedAccessorySlotAccess;
import me.pajic.accessorify.keybind.ModScrollHandler;
import me.pajic.accessorify.menu.ShulkerBoxAccessoryContainerMenu;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModNetworking {

    public static final ResourceLocation OPEN_SHULKER_BOX = MultiVersionUtil.withModNamespace(  "open_shulker_box");
    public static final ResourceLocation OPEN_ENDER_CONTAINER = MultiVersionUtil.withModNamespace(  "open_ender_container");
    public static final ResourceLocation C2S_SYNC_SHULKER_SLOT = MultiVersionUtil.withModNamespace(  "c2s_sync_shulker_slot");
    public static final ResourceLocation S2C_SYNC_SHULKER_SLOT = MultiVersionUtil.withModNamespace(  "s2c_sync_shulker_slot");
    public static final ResourceLocation C2S_SYNC_ARROW_SLOT = MultiVersionUtil.withModNamespace(  "c2s_sync_arrow_slot");
    public static final ResourceLocation S2C_SYNC_ARROW_SLOT = MultiVersionUtil.withModNamespace(  "s2c_sync_arrow_slot");

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

    @SubscribeEvent
    public static void init(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                C2SOpenShulkerBoxPayload.TYPE,
                C2SOpenShulkerBoxPayload.CODEC,
                (payload, context) -> {
                    Player player = context.player();
                    Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
                    if (ac.isPresent()) {
                        player.openMenu(new ShulkerBoxAccessoryContainerMenu(ac.get().getContainers().get("shulker").getAccessories().getItem(payload.index), 27));
                        player.awardStat(Stats.OPEN_SHULKER_BOX);
                    }
                }
        );
        registrar.playToServer(
                C2SOpenEnderContainerPayload.TYPE,
                C2SOpenEnderContainerPayload.CODEC,
                (payload, context) -> {
                    context.player().playSound(SoundEvents.ENDER_CHEST_OPEN);
                    PlayerEnderChestContainer container = context.player().getEnderChestInventory();
                    context.player().openMenu(new SimpleMenuProvider((i, inventory, player1) ->
                            ChestMenu.threeRows(i, inventory, container), Component.translatable("container.enderchest")
                    ));
                    context.player().awardStat(Stats.OPEN_ENDERCHEST);
                }
        );
        registrar.playToServer(
                C2SSyncShulkerSlot.TYPE,
                C2SSyncShulkerSlot.CODEC,
                (payload, context) ->
                        ((SelectedAccessorySlotAccess) context.player()).accessorify$setShulkerSlot(payload.slot)
        );
        registrar.playToServer(
                C2SSyncArrowSlot.TYPE,
                C2SSyncArrowSlot.CODEC,
                (payload, context) ->
                        ((SelectedAccessorySlotAccess) context.player()).accessorify$setArrowSlot(payload.slot)
        );
        registrar.playToClient(
                S2CSyncShulkerSlot.TYPE,
                S2CSyncShulkerSlot.CODEC,
                (payload, context) -> ModScrollHandler.selectedShulkerSlot = payload.slot
        );
        registrar.playToClient(
                S2CSyncArrowSlot.TYPE,
                S2CSyncArrowSlot.CODEC,
                (payload, context) -> ModScrollHandler.selectedArrowSlot = payload.slot
        );
    }
}
