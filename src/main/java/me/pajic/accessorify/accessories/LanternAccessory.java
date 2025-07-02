package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LanternAccessory implements SlotCopyingAccessory{

    public static void init() {
        MultiVersionUtil.registerAccessory(Items.LANTERN, new LanternAccessory());
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        AccessoriesRendererRegistry.registerRenderer(Items.LANTERN, LanternAccessoryRenderer::new);
    }

    @Override
    public String getPath() {
        return "add_belt_1";
    }

    @Override
    public String getSlot() {
        return "belt";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, ModUtil::isLantern);
    }
}
