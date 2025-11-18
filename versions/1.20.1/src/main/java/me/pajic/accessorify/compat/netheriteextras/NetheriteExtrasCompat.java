package me.pajic.accessorify.compat.netheriteextras;

import net.minecraft.world.item.ItemStack;
import xyz.hafemann.netheriteextras.item.ModItems;

public class NetheriteExtrasCompat {

    public static boolean isTotem(ItemStack stack) {
        return stack.is(ModItems.TOTEM_OF_NEVERDYING);
    }
}
