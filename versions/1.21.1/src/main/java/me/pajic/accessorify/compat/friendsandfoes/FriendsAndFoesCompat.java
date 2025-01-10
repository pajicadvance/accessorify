package me.pajic.accessorify.compat.friendsandfoes;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FriendsAndFoesCompat {

    public static boolean isTotem(ItemStack stack) {
        return stack.is(Items.TOTEM_OF_UNDYING) || stack.is(FriendsAndFoesTags.TOTEMS);
    }

    public static ItemStack getTotemAccessoryStack(Player player) {
        ItemStack stack = ModUtil.getAccessoryStack(player, FriendsAndFoesItems.TOTEM_OF_FREEZING.get());
        if (stack.isEmpty()) {
            stack = ModUtil.getAccessoryStack(player, FriendsAndFoesItems.TOTEM_OF_ILLUSION.get());
        }
        return stack;
    }
}
