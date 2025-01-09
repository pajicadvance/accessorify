package me.pajic.accessorify.compat.deeperdarker;

import com.kyanite.deeperdarker.content.DDItems;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DeeperDarkerCompat {

    public static ItemStack getSoulElytraAccessoryStack(LivingEntity entity) {
        return ModUtil.getAccessoryStack(entity, DDItems.SOUL_ELYTRA);
    }
}
