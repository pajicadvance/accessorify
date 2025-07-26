package me.pajic.accessorify.mixin.compat.extrasounds;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.arbor.extrasoundsnext.sounds.SoundManager;
import me.pajic.accessorify.util.ModUtil;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("extrasounds")
@Mixin(value = SoundManager.class, remap = false)
public class SoundManagerMixin {

    @WrapMethod(method = "hotbar")
    private static void noHotbarScrollSoundIfScoping(int i, Operation<Void> original) {
        if (!ModUtil.shouldScope) original.call(i);
    }
}
