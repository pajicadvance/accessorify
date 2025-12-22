package me.pajic.accessorify.mixin.compat.sa;

//? if fabric {

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import com.nettakrim.spyglass_astronomy.SpyglassAstronomyClient;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("spyglass_astronomy")
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(SpyglassAstronomyClient.class)
public class SpyglassAstronomyClientMixin {

	@Shadow
	public static Minecraft client;

	@ModifyExpressionValue(
			method = "update",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/player/LocalPlayer;isScoping()Z"
			)
	)
	private static boolean checkAccessoryScoping(boolean original) {
		return original || ClientUtil.shouldScope;
	}

	//? if > 1.21.1 {
	@ModifyReturnValue(
			method = "isntHoldingSpyglass",
			at = @At(
					value = "RETURN",
					ordinal = 1
			)
	)
	private static boolean checkAccessorySlot(boolean original) {
		return original && !AccessoryUtil.isAccessoryEquipped(client.player, Items.SPYGLASS);
	}
	//?} else {
	/*@ModifyReturnValue(
			method = "isHoldingSpyglass",
			at = @At(
					value = "RETURN",
					ordinal = 1
			)
	)
	private static boolean checkAccessorySlot(boolean original) {
		return original || AccessoryUtil.isAccessoryEquipped(client.player, Items.SPYGLASS);
	}
	*///?}
}
//?}
