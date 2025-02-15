package me.pajic.accessorify.mixin.compat.sereneseasons;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sereneseasons.init.ModClient;

@IfModLoaded("sereneseasons")
@Mixin(value = ModClient.class, remap = false)
public class ModClientMixin {

    @Inject(
            method = "registerItemProperties",
            at = @At("TAIL")
    )
    private static void onRegister(CallbackInfo ci) {
        if (Main.CONFIG.calendarAccessory()) SereneSeasonsCalendarAccessory.clientInit();
    }
}
