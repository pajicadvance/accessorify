package me.pajic.accessorify.compat.deeperdarker;

import com.kyanite.deeperdarker.content.DDItems;
import it.unimi.dsi.fastutil.booleans.BooleanObjectImmutablePair;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DeeperDarkerCompat {
    public static BooleanObjectImmutablePair<ItemStack> getSoulElytraAccessoryStack(LivingEntity entity) {
        return ModUtil.getAccessoryStack(entity, DDItems.SOUL_ELYTRA.get());
    }

    public static void init() {
        MultiVersionUtil.noRenderer(DDItems.SOUL_ELYTRA.get());
    }
}
