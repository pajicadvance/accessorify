package me.pajic.accessorify.compat.deeperdarker;

import com.kyanite.deeperdarker.content.DDItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ElytraItem;

public class SoulElytraAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(DDItems.SOUL_ELYTRA, new SoulElytraAccessory());
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        AccessoriesRendererRegistry.registerNoRenderer(DDItems.SOUL_ELYTRA);
    }

    @Override
    public String getPath() {
        return "add_cape_1";
    }

    @Override
    public String getSlot() {
        return "cape";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference,
                itemStack -> itemStack.getItem() instanceof ElytraItem
        );
    }
}
