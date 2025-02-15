package me.pajic.accessorify.mixin.compat.fabricseasons;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import io.github.lucaargolo.seasonsextras.client.FabricSeasonsExtrasClient;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.accessories.compat.FabricSeasonsCalendarAccessory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@IfModLoaded("seasonsextras")
@Mixin(value = FabricSeasonsExtrasClient.class, remap = false)
public class FabricSeasonsClientExtrasMixin {

    @Inject(
            method = "onInitializeClient",
            at = @At("TAIL")
    )
    private void onInitialize(CallbackInfo ci) {
        if (Main.FABRIC_SEASONS_LOADED && Main.CONFIG.calendarAccessory()) FabricSeasonsCalendarAccessory.clientInit();
    }
}
