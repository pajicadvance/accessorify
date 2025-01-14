package me.pajic.accessorify.mixin.compat.friendsandfoes;

import com.faboslav.friendsandfoes.fabric.modcompat.TrinketsCompat;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.compat.friendsandfoes.FriendsAndFoesCompat;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Predicate;

@IfModLoaded("friendsandfoes")
@Mixin(TrinketsCompat.class)
public class TrinketsCompatMixin {

    @WrapMethod(method = "getEquippedItemFromCustomSlots")
    private @Nullable ItemStack addAccessoriesCompat(Entity entity, Predicate<ItemStack> itemStackPredicate, Operation<ItemStack> original) {
        if (Main.CONFIG.totemOfUndyingAccessory() && entity instanceof Player player) {
            ItemStack stack = FriendsAndFoesCompat.getTotemAccessoryStack(player);
            if (stack.isEmpty()) {
                stack = ModUtil.getAccessoryStack(player, Items.TOTEM_OF_UNDYING);
            }
            return stack.isEmpty() ? original.call(entity, itemStackPredicate) : stack;
        }
        return original.call(entity, itemStackPredicate);
    }
}
