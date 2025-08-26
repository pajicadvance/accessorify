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
        //? if > 1.21.4 {
        /*AccessoriesRendererRegistry.registerRenderer(MultiVersionUtil.withModNamespace("lantern_renderer"), LanternAccessoryRenderer::new);
        ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.bindItemToRenderer(item, MultiVersionUtil.withModNamespace("lantern_renderer")));
        *///?}
        //? if <= 1.21.4
        ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.registerRenderer(item, LanternAccessoryRenderer::new));
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
