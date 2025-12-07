package me.pajic.accessorify.mixin.compat.neoforge;

//? if >= 1.21.10 && neoforge {

/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.accessorify.util.FakeHandHolder;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.transfer.item.PlayerInventoryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(PlayerInventoryWrapper.class)
public class PlayerInventoryWrapperMixin {

	@ModifyExpressionValue(
			method = "insert(Lnet/neoforged/neoforge/transfer/item/ItemResource;ILnet/neoforged/neoforge/transfer/transaction/TransactionContext;)I",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/InteractionHand;values()[Lnet/minecraft/world/InteractionHand;"
			)
	)
	private InteractionHand[] skipFakeHand(InteractionHand[] original) {
		ArrayList<InteractionHand> list = new ArrayList<>(Arrays.asList(original));
		list.remove(FakeHandHolder.FAKE_HAND);
		return list.toArray(new InteractionHand[list.size() - 1]);
	}
}
*///?}
