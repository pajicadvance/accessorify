package me.pajic.accessorify.mixin.compat.friendsandfoes;

import com.bawnorton.mixinsquared.TargetHandler;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("friendsandfoes")
@Mixin(value = Player.class, priority = 1500)
public class PlayerEntityMixinSquared {
    @TargetHandler(
            mixin = "com.faboslav.friendsandfoes.common.mixin.PlayerEntityMixin",
            name = "friendsandfoes_getTotemFromHands"
    )
    @IfModLoaded("friendsandfoes")
    @ModifyReturnValue(
            method = "@MixinSquared:Handler",
            at = @At("RETURN")
    )
    private static ItemStack tryGetTotemAccessory(ItemStack original, @Local Player player) {
        if (Main.CONFIG.totemOfUndyingAccessory()) {
            ItemStack stack = ModUtil.getAccessoryStack(player, FriendsAndFoesItems.TOTEM_OF_FREEZING.get());
            if (stack.isEmpty()) {
                stack = ModUtil.getAccessoryStack(player, FriendsAndFoesItems.TOTEM_OF_ILLUSION.get());
            }
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
}
