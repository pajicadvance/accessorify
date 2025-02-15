package me.pajic.accessorify.mixin.compat.sereneseasons;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sereneseasons.init.ModItems;

@IfModLoaded("sereneseasons")
@Mixin(value = ModItems.class, remap = false)
public class ModItemsMixin {

    @Inject(
            method = "registerItems",
            at = @At("TAIL")
    )
    private static void onRegister(CallbackInfo ci) {
        if (Main.CONFIG.calendarAccessory()) SereneSeasonsCalendarAccessory.init();
    }
}
