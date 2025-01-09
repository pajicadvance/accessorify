package me.pajic.accessorify.compat.friendsandfoes;

import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FriendsAndFoesCompat {

    public static boolean isTotem(ItemStack stack) {
        return stack.is(Items.TOTEM_OF_UNDYING) || stack.is(FriendsAndFoesTags.TOTEMS);
    }
}
