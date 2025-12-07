package me.pajic.accessorify.mixin;

import me.pajic.accessorify.util.FakeHandHolder;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(InteractionHand.class)
public class InteractionHandMixin {

	@Shadow @Final @Mutable private static InteractionHand[] $VALUES;

	@Invoker("<init>")
	static InteractionHand invokeInit(String name, int id) {
		throw new UnsupportedOperationException();
	}

	static {
		ArrayList<InteractionHand> list = new ArrayList<>(Arrays.asList($VALUES));
		int size = list.size();
		FakeHandHolder.FAKE_HAND = invokeInit("FAKE_HAND", size);
		list.add(FakeHandHolder.FAKE_HAND);
		$VALUES = list.toArray(new InteractionHand[size + 1]);
	}
}
