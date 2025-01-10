package me.pajic.accessorify.mixin.compat.deeperdarker;

import com.kyanite.deeperdarker.content.DDItems;
import com.kyanite.deeperdarker.network.SoulElytraBoostPacket;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("deeperdarker")
@Mixin(SoulElytraBoostPacket.class)
public class SoulElytraBoostPacketMixin {

    @ModifyExpressionValue(
            method = "lambda$handle$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private static boolean checkSoulElytraAccessory(boolean original, @Local Player player) {
        return original || !ModUtil.getAccessoryStack(player, DDItems.SOUL_ELYTRA.get()).isEmpty();
    }
}
