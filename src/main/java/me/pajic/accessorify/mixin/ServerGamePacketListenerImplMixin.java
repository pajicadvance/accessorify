package me.pajic.accessorify.mixin;

import me.pajic.accessorify.access.SelectedAccessorySlotAccess;
import me.pajic.accessorify.network.ModNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void syncSlots(MinecraftServer server, Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        ServerPlayNetworking.send(player, new ModNetworking.S2CSyncShulkerSlot(((SelectedAccessorySlotAccess) player).accessorify$getShulkerSlot()));
        ServerPlayNetworking.send(player, new ModNetworking.S2CSyncArrowSlot(((SelectedAccessorySlotAccess) player).accessorify$getArrowSlot()));
    }
}
