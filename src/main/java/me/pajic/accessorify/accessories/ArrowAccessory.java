package me.pajic.accessorify.accessories;

//$ accessory
import io.wispforest.accessories.api.core.Accessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;

public class ArrowAccessory implements Accessory {

    public static void init() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
                registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
                        MultiVersionUtil.registerAccessory(itemHolder.value(), new ArrowAccessory())
                )
        );
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
                registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
                        MultiVersionUtil.noRenderer(itemHolder.value())
                )
        );
    }
}
