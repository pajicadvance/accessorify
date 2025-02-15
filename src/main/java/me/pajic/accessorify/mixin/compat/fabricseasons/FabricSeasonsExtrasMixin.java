package me.pajic.accessorify.mixin.compat.fabricseasons;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.accessories.compat.FabricSeasonsCalendarAccessory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@IfModLoaded("seasonsextras")
@Mixin(value = FabricSeasonsExtras.class, remap = false)
public class FabricSeasonsExtrasMixin {

    @Inject(
            method = "onInitialize",
            at = @At("TAIL")
    )
    private void onInitialize(CallbackInfo ci) {
        if (Main.FABRIC_SEASONS_LOADED && Main.CONFIG.calendarAccessory()) FabricSeasonsCalendarAccessory.init();
    }
}
