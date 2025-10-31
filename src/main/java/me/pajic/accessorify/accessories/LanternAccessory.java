package me.pajic.accessorify.accessories;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LanternAccessory implements SlotCopyingAccessory{

    public static void init() {
        //? if > 1.21.1 {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
                registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.LANTERNS).forEach(itemHolder ->
                        MultiVersionUtil.registerAccessory(itemHolder.value(), new LanternAccessory())
                )
        );
        //?}
        //? if <= 1.21.1
        /*MultiVersionUtil.registerAccessory(Items.LANTERN, new LanternAccessory());*/
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        //? if > 1.21.1 {
        AccessoriesRendererRegistry.registerRenderer(MultiVersionUtil.withModNamespace("lantern_renderer"), LanternAccessoryRenderer::new);
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
                registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.LANTERNS).forEach(itemHolder ->
                        AccessoriesRendererRegistry.bindItemToRenderer(itemHolder.value(), MultiVersionUtil.withModNamespace("lantern_renderer"))
                )
        );
        //?}
        //? if <= 1.21.1
        /*ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.registerRenderer(item, LanternAccessoryRenderer::new));*/
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
