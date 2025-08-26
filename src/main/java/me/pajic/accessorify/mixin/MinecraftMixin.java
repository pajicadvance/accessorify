package me.pajic.accessorify.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.accessorify.keybind.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Final
    public Options options;

    @Inject(
            method = "run",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;gameThread:Ljava/lang/Thread;",
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void onClientStart(CallbackInfo ci) {
        if (options.keyLoadHotbarActivator.matches(ModKeybinds.OPEN_WIDGET.get().getKey().getValue(), -1)) {
            options.keyLoadHotbarActivator.setKey(InputConstants.UNKNOWN);
        }
        if (options.keySaveHotbarActivator.matches(ModKeybinds.USE_SPYGLASS.get().getKey().getValue(), -1)) {
            options.keySaveHotbarActivator.setKey(InputConstants.UNKNOWN);
        }
    }
}
