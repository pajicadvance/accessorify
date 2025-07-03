package me.pajic.accessorify.accessories.compat;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

public class AdditionalLanternAccessory implements SlotCopyingAccessory {

    public static void init() {
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
            if (rl.getNamespace().equals("additionallanterns") && rl.getPath().endsWith("_lantern")) {
                MultiVersionUtil.registerAccessory(item, new AdditionalLanternAccessory());
                ModUtil.LANTERNS.add(item);
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.registerRenderer(item, LanternAccessoryRenderer::new));
    }

    @Override
    public String getPath() {
        return "add_belt_3";
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
