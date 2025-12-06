package me.pajic.accessorify.mixin;

import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.NetworkUtil;
import me.pajic.accessorify.util.PlayerExtension;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >= 1.21.1
import net.minecraft.server.network.CommonListenerCookie;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void syncSlots(MinecraftServer server, Connection connection, ServerPlayer player, /*? if >= 1.21.1 {*/CommonListenerCookie cookie,/*?}*/ CallbackInfo ci) {
        NetworkUtil.S2C(new Payloads.S2CSyncShulkerSlot(((PlayerExtension) player).accessorify$getShulkerSlot()), player);
		NetworkUtil.S2C(new Payloads.S2CSyncArrowSlot(((PlayerExtension) player).accessorify$getArrowSlot()), player);
    }
}
