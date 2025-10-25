package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @WrapMethod(method = "getFieldOfViewModifier")
    //? if <= 1.21.1
    /*private float modifyFOV(Operation<Float> original) {*/
    //? if > 1.21.1
    private float modifyFOV(boolean isFirstPerson, float fovEffectScale, Operation<Float> original) {
        if (ModUtil.shouldScope) {
            return 0.1F * ModUtil.zoomModifier;
        }
        else if (ModUtil.zoomModifier != 1.0F && (!ClientMain.CLIENT_CONFIG.spyglassZoomSettings.rememberZoomLevel.get() || !ClientMain.CLIENT_CONFIG.spyglassZoomSettings.scrollableZoom.get())) {
            ModUtil.zoomModifier = 1.0F;
        }
        //? if <= 1.21.1
        /*return original.call();*/
        //? if > 1.21.1
        return original.call(isFirstPerson, fovEffectScale);
    }
}
