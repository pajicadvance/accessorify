package me.pajic.accessorify.compat.netheriteextras;

import xyz.hafemann.netheriteextras.item.ModItems;
import net.minecraft.world.item.ItemStack;

public class NetheriteExtrasCompat {

    public static boolean isTotem(ItemStack stack) {
        return stack.is(ModItems.TOTEM_OF_NEVERDYING);
    }
}
