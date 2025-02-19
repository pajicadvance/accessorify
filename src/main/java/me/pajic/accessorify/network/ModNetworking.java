package me.pajic.accessorify.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.menu.ShulkerBoxAccessoryContainerMenu;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModNetworking {

    public static final ResourceLocation OPEN_SHULKER_BOX = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "open_shulker_box");

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

    public static void init() {
        PayloadTypeRegistry.playC2S().register(C2SOpenShulkerBoxPayload.TYPE, C2SOpenShulkerBoxPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(C2SOpenShulkerBoxPayload.TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                player.openMenu(new ShulkerBoxAccessoryContainerMenu(ac.get().getContainers().get("shulker").getAccessories().getItem(payload.index)));
                player.awardStat(Stats.OPEN_SHULKER_BOX);
            }
        });
    }
}
