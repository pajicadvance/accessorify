package me.pajic.accessorify.mixin.compat.friendsandfoes;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.compat.friendsandfoes.FriendsAndFoesCompat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Player.class, priority = 1500)
public class PlayerEntityMixinSquared {
    @TargetHandler(
            mixin = "com.faboslav.friendsandfoes.common.mixin.PlayerEntityMixin",
            name = "friendsandfoes_getTotemFromHands"
    )
    @ModifyReturnValue(
            method = "@MixinSquared:Handler",
            at = @At("RETURN")
    )
    private static ItemStack tryGetTotemAccessory(ItemStack original, @Local Player player) {
        if (Main.CONFIG.totemOfUndyingAccessory() && Main.FRIENDS_AND_FOES_LOADED) {
            ItemStack stack = FriendsAndFoesCompat.getTotemAccessoryStack(player);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}
