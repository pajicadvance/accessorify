package me.pajic.accessorify.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
//? if >= 1.21.1 {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//?} else {
/*import me.fzzyhmstrs.fzzy_config.networking.FzzyPayload;
*///?}

public class Payloads {
    //? if >= 1.21.1 {
    public record C2SOpenShulkerBoxPayload(int index) implements CustomPacketPayload {
        public static final Type<C2SOpenShulkerBoxPayload> TYPE = new Type<>(NetworkConstants.OPEN_SHULKER_BOX);
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
        public static final Type<C2SOpenEnderContainerPayload> TYPE = new Type<>(NetworkConstants.OPEN_ENDER_CONTAINER);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SOpenEnderContainerPayload> CODEC = StreamCodec.unit(
                new C2SOpenEnderContainerPayload()
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record C2SSyncShulkerSlot(int slot) implements CustomPacketPayload {
        public static final Type<C2SSyncShulkerSlot> TYPE = new Type<>(NetworkConstants.C2S_SYNC_SHULKER_SLOT);
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
        public static final Type<S2CSyncShulkerSlot> TYPE = new Type<>(NetworkConstants.S2C_SYNC_SHULKER_SLOT);
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
        public static final Type<C2SSyncArrowSlot> TYPE = new Type<>(NetworkConstants.C2S_SYNC_ARROW_SLOT);
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
        public static final Type<S2CSyncArrowSlot> TYPE = new Type<>(NetworkConstants.S2C_SYNC_ARROW_SLOT);
        public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncArrowSlot> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, S2CSyncArrowSlot::slot,
                S2CSyncArrowSlot::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
    //?} else {
    /*public record C2SOpenShulkerBoxPayload(int index) implements FzzyPayload {
        @Override
        public void write(@NotNull FriendlyByteBuf buf) {
            buf.writeInt(index);
        }
        @Override
        public @NotNull ResourceLocation getId() {
            return NetworkConstants.OPEN_SHULKER_BOX;
        }
    }

    public record C2SOpenEnderContainerPayload() implements FzzyPayload {
        @Override
        public void write(@NotNull FriendlyByteBuf buf) {}
        @Override
        public @NotNull ResourceLocation getId() {
            return NetworkConstants.OPEN_ENDER_CONTAINER;
        }
    }

    public record C2SSyncShulkerSlot(int slot) implements FzzyPayload {
        @Override
        public void write(@NotNull FriendlyByteBuf buf) {
            buf.writeInt(slot);
        }
        @Override
        public @NotNull ResourceLocation getId() {
            return NetworkConstants.C2S_SYNC_SHULKER_SLOT;
        }
    }

    public record S2CSyncShulkerSlot(int slot) implements FzzyPayload {
        @Override
        public void write(@NotNull FriendlyByteBuf buf) {
            buf.writeInt(slot);
        }
        @Override
        public @NotNull ResourceLocation getId() {
            return NetworkConstants.S2C_SYNC_SHULKER_SLOT;
        }
    }

    public record C2SSyncArrowSlot(int slot) implements FzzyPayload {
        @Override
        public void write(@NotNull FriendlyByteBuf buf) {
            buf.writeInt(slot);
        }
        @Override
        public @NotNull ResourceLocation getId() {
            return NetworkConstants.C2S_SYNC_ARROW_SLOT;
        }
    }

    public record S2CSyncArrowSlot(int slot) implements FzzyPayload {
        @Override
        public void write(@NotNull FriendlyByteBuf buf) {
            buf.writeInt(slot);
        }
        @Override
        public @NotNull ResourceLocation getId() {
            return NetworkConstants.S2C_SYNC_ARROW_SLOT;
        }
    }
    *///?}
}
