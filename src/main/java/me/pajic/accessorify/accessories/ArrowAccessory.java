package me.pajic.accessorify.accessories;

//$ Accessory
import io.wispforest.accessories.api.Accessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

public class ArrowAccessory implements Accessory {
    @SubscribeEvent
    public static void init(TagsUpdatedEvent event) {
        //? if <= 1.21.1
        event.getRegistryAccess()
        //? if >= 1.21.4
        /*event.getLookupProvider()*/
                .lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
                        MultiVersionUtil.registerAccessory(itemHolder.value(), new ArrowAccessory())
        );
    }
}
