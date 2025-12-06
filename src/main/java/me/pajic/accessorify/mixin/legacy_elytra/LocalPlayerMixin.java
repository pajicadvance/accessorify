package me.pajic.accessorify.mixin.legacy_elytra;

//? if < 1.21.10 {

/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @ModifyExpressionValue(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (Accessorify.CONFIG.accessorySettings.elytraAccessory.get()) {
            ItemStack stack = AccessoryUtil.getAccessoryStack((LivingEntity) (Object) this, GameplayUtil::isElytra);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}
*///?}
