package me.pajic.accessorify.util.compat;

import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.world.item.ItemStack;

public class FriendsAndFoesCompat {
    public static boolean isTotem(ItemStack stack) {
        return stack.is(FriendsAndFoesTags.TOTEMS);
    }
}
