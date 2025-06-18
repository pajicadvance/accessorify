package me.pajic.accessorify.mixin;

import me.pajic.accessorify.access.SelectedAccessorySlotAccess;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.MultiVersionUtil;
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
        MultiVersionUtil.S2C(new Payloads.S2CSyncShulkerSlot(((SelectedAccessorySlotAccess) player).accessorify$getShulkerSlot()), player);
        MultiVersionUtil.S2C(new Payloads.S2CSyncArrowSlot(((SelectedAccessorySlotAccess) player).accessorify$getArrowSlot()), player);
    }
}
