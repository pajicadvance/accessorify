package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.AccessorifyClient;
import me.pajic.accessorify.util.ClientUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @WrapMethod(method = "getFieldOfViewModifier")
    //? if <= 1.21.1
    //private float modifyFOV(Operation<Float> original) {
    //? if > 1.21.1
    private float modifyFOV(boolean isFirstPerson, float fovEffectScale, Operation<Float> original) {
        if (ClientUtil.shouldScope) {
            return 0.1F * ClientUtil.zoomModifier;
        }
        else if (ClientUtil.zoomModifier != 1.0F && (!AccessorifyClient.CONFIG.spyglassZoomSettings.rememberZoomLevel.get() || !AccessorifyClient.CONFIG.spyglassZoomSettings.scrollableZoom.get())) {
			ClientUtil.zoomModifier = 1.0F;
        }
        //? if <= 1.21.1
        //return original.call();
        //? if > 1.21.1
        return original.call(isFirstPerson, fovEffectScale);
    }
}
