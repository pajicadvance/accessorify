package me.pajic.accessorify.compat.arselixirum;

import dev.obscuria.elixirum.registry.ElixirumItems;
import net.minecraft.world.item.ItemStack;

public class ArsElixirumCompat {

    public static boolean isTotem(ItemStack stack) {
        return stack.is(ElixirumItems.WITCH_TOTEM_OF_UNDYING.value());
    }
}
