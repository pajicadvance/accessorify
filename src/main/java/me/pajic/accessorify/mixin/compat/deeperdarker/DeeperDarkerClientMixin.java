package me.pajic.accessorify.mixin.compat.deeperdarker;

import com.kyanite.deeperdarker.DeeperDarkerClient;
import com.kyanite.deeperdarker.content.DDItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("deeperdarker")
@Mixin(DeeperDarkerClient.class)
public class DeeperDarkerClientMixin {

    @ModifyExpressionValue(
            method = "lambda$onInitializeClient$6",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack tryGetSoulElytraAccessory(ItemStack original, @Local Minecraft mc) {
        if (Main.CONFIG.elytraAccessory()) {
            ItemStack stack = ModUtil.getAccessoryStack(mc.player, DDItems.SOUL_ELYTRA);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "lambda$onInitializeClient$9",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack tryGetSoulElytraAccessory1(ItemStack original, @Local Minecraft mc) {
        if (Main.CONFIG.elytraAccessory()) {
            ItemStack stack = ModUtil.getAccessoryStack(mc.player, DDItems.SOUL_ELYTRA);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}
