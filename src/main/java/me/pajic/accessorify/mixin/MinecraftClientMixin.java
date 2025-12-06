package me.pajic.accessorify.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.keybind.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftClientMixin {

	@Inject(
			method = "updateLevelInEngines",
			at = @At("TAIL")
	)
	private void afterClientWorldChange(ClientLevel level, CallbackInfo ci) {
		ModKeybinds.onClientStarted(Minecraft.getInstance());
	}
}
