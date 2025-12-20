package me.pajic.accessorify.mixin.compat.fabricapi;

//? fabric {

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.FakeHandHolder;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(targets = "net/fabricmc/fabric/impl/transfer/item/PlayerInventoryStorageImpl")
public class PlayerInventoryStorageImplMixin {

	@ModifyExpressionValue(
			method = "offer",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/InteractionHand;values()[Lnet/minecraft/world/InteractionHand;"
			)
	)
	private InteractionHand[] skipFakeHand(InteractionHand[] original) {
		if (Accessorify.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
			ArrayList<InteractionHand> list = new ArrayList<>(Arrays.asList(original));
			list.remove(FakeHandHolder.FAKE_HAND);
			return list.toArray(new InteractionHand[list.size() - 1]);
		}
		return original;
	}
}
//?}
