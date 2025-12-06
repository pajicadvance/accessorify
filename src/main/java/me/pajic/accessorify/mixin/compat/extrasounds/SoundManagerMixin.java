package me.pajic.accessorify.mixin.compat.extrasounds;

//? if < 1.21.10 {

/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.arbor.extrasoundsnext.sounds.SoundManager;
import me.pajic.accessorify.util.ClientUtil;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("extrasounds")
@Mixin(value = SoundManager.class, remap = false)
public class SoundManagerMixin {

    @WrapMethod(method = "hotbar")
    private static void noHotbarScrollSoundIfScoping(int i, Operation<Void> original) {
        if (!ClientUtil.shouldScope) original.call(i);
    }
}
*///?}
