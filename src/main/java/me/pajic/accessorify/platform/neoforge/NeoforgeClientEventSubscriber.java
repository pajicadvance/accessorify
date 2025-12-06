package me.pajic.accessorify.platform.neoforge;

//? neoforge {

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.platform.Platform;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.CompatFlags;
import me.pajic.accessorify.util.GeneralUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;

@EventBusSubscriber(modid = Accessorify.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {

	private static boolean tagEventsProcessed = false;

	@SubscribeEvent
	public static void onClientSetup(final FMLClientSetupEvent event) {
		Accessorify.onInitializeClient();
	}

	@SubscribeEvent
	private static void initClientResources(AddPackFindersEvent event) {
		event.addPackFinders(
				Accessorify.id(Accessorify.xplat().packPath(Platform.VersionedPackType.ASSETS)),
				PackType.CLIENT_RESOURCES,
				Component.literal("Mod " + Accessorify.xplat().mcVersion() + " Resource Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
	}

	@SubscribeEvent
	private static void initRegistry(FMLClientSetupEvent event) {
		//? if > 1.21.1
		AccessoriesRendererRegistry.registerRenderer(Accessorify.id("lantern_renderer"), LanternAccessoryRenderer::new);
		AccessoryUtil.registerEmptyRenderer(
				Items.CLOCK, Items.COMPASS, Items.ELYTRA, Items.ENDER_CHEST,
				Items.RECOVERY_COMPASS, Items.SPYGLASS, Items.TOTEM_OF_UNDYING
		);
	}

	@SubscribeEvent
	private static void initRegistryAddedCallbacks(ModifyRegistriesEvent event) {
		event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, item) -> {
			if (CompatFlags.SERENE_SEASONS_LOADED && key.location().equals(SereneSeasonsCalendarAccessory.ITEM_ID)) {
				AccessoryUtil.registerEmptyRenderer(item);
			}
		});
	}

	@SubscribeEvent
	private static void initTagsLoadedEvents(TagsUpdatedEvent event) {
		if (!tagEventsProcessed) {
			HolderLookup<Item> lookup = event./*? if < 1.21.10 {*//*getRegistryAccess()*//*?} else {*/getLookupProvider()/*?}*/.lookupOrThrow(Registries.ITEM);
			lookup.getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
					AccessoryUtil.registerEmptyRenderer(itemHolder.value())
			);
			lookup.getOrThrow(GeneralUtil.LANTERNS).forEach(itemHolder ->
					AccessoryUtil.bindItemToLanternRenderer(itemHolder.value())
			);
			lookup.getOrThrow(GeneralUtil.SHULKER_BOXES).forEach(blockHolder ->
					AccessoryUtil.registerEmptyRenderer(blockHolder.value().asItem())
			);
			tagEventsProcessed = true;
		}
	}

	@SubscribeEvent
	private static void initKeybinds(RegisterKeyMappingsEvent event) {
		//? if >= 1.21.10
		event.registerCategory(ModKeybinds.MOD_KEYS);
		event.register(ModKeybinds.OPEN_WIDGET);
		event.register(ModKeybinds.OPEN_ENDER_CHEST);
		event.register(ModKeybinds.USE_SPYGLASS);
	}

	@SubscribeEvent
	private static void onClientTick(ClientTickEvent.Post event) {
		ModKeybinds.onClientTick(Minecraft.getInstance());
	}
}
//?}
