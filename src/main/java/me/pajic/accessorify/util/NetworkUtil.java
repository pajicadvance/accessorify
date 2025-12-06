package me.pajic.accessorify.util;

//? if fabric {
/*//? if <= 1.20.1 {
/^import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.networking.FzzyPayload;
import net.minecraft.world.entity.player.Player;
^///?} else {
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//?}
*///?} else {
import net.neoforged.neoforge.network.PacketDistributor;
//? if > 1.21.1
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
//?}
//? if > 1.20.1
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class NetworkUtil {

	//? if fabric {
	/*//? if > 1.20.1 {
	public static void C2S(CustomPacketPayload payload) {
		ClientPlayNetworking.send(payload);
	}
	public static void S2C(CustomPacketPayload payload, ServerPlayer player) {
		ServerPlayNetworking.send(player, payload);
	}
	//?} else {
    /^public static void C2S(FzzyPayload payload) {
        ConfigApiJava.network().send(payload, null);
    }
    public static void S2C(FzzyPayload payload, Player player) {
        ConfigApiJava.network().send(payload, player);
    }
    ^///?}
	*///?} else {
	public static void C2S(CustomPacketPayload payload) {
		//? if <= 1.21.1
		//PacketDistributor.sendToServer(payload);
		//? if > 1.21.1
		ClientPacketDistributor.sendToServer(payload);
	}
	public static void S2C(CustomPacketPayload payload, ServerPlayer player) {
		PacketDistributor.sendToPlayer(player, payload);
	}
	//?}
}
