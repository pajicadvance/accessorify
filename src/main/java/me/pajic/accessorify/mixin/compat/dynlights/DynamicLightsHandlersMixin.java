package me.pajic.accessorify.mixin.compat.dynlights;

//? if 1.20.1 {

/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("sodiumdynamiclights")
@Mixin(value = DynamicLightHandlers.class, remap = false)
public class DynamicLightsHandlersMixin {
    // https://github.com/txnimc/ImmersiveLanterns/blob/main/src/main/java/toni/immersivelanterns/foundation/mixin/DynamicLightsMixin.java
    @WrapMethod(method = "registerDefaultHandlers")
    private static void onRegisterDynLights(Operation<Void> original) {
        original.call();
        DynamicLightHandlers.registerDynamicLightHandler(EntityType.PLAYER, DynamicLightHandler.makeHandler(player -> {
            if (AccessoryUtil.isAccessoryEquipped(player, Items.SOUL_LANTERN)) return 10;
			if (AccessoryUtil.isAccessoryEquipped(player, GameplayUtil::isLantern)) return 15;
            return 0;
        }, player -> true));
    }
}
*///?}
