package me.pajic.accessorify.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.menu.ShulkerBoxAccessoryContainerMenu;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ModNetworking {

    public static final ResourceLocation OPEN_SHULKER_BOX = ResourceLocation.fromNamespaceAndPath("accessorify", "open_shulker_box");

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
                        List<SlotEntryReference> shulkerBoxes = ac.get().getEquipped(ModUtil::isShulkerBox);
                        player.openMenu(new ShulkerBoxAccessoryContainerMenu(shulkerBoxes.get(payload.index).stack()));
                        player.awardStat(Stats.OPEN_SHULKER_BOX);
                    }
                }
        );
    }
}
