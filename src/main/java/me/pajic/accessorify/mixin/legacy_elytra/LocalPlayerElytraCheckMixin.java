package me.pajic.accessorify.mixin.legacy_elytra;

//? if < 1.21.10 && fabric {

/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerElytraCheckMixin {

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean modifyElytraCheck(boolean original, @Local ItemStack itemStack) {
        return AccessoryUtil.moddedElytraCheck(itemStack, (LivingEntity) (Object) this, original);
    }
}
*///?}
