package me.pajic.accessorify.platform.fabric;

//? fabric {

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.accessories.compat.FabricSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.network.NetworkClientEvents;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.platform.Platform;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.CompatFlags;
import me.pajic.accessorify.util.GeneralUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
//
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
//? if > 1.20.1 {
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//?} else {
/*import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.accessorify.network.NetworkConstants;
*///?}

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	private static boolean tagEventsProcessed = false;

	@Override
	public void onInitializeClient() {
		Accessorify.onInitializeClient();
		initConditionalClientResources();
		initRegistry();
		initRegistryAddedCallbacks();
		initTagsLoadedEvents();
		initKeybinds();
		initNetworking();
	}

	private static void initConditionalClientResources() {
		FabricLoader.getInstance().getModContainer(Accessorify.MOD_ID).ifPresent(modContainer ->
				ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(Accessorify.xplat().packPath(Platform.VersionedPackType.ASSETS)),
						modContainer,
						ResourcePackActivationType.ALWAYS_ENABLED
				)
		);
	}

	private static void initRegistry() {
		//? if > 1.21.1
		AccessoriesRendererRegistry.registerRenderer(Accessorify.id("lantern_renderer"), LanternAccessoryRenderer::new);
		AccessoryUtil.registerEmptyRenderer(
				Items.CLOCK, Items.COMPASS, Items.ELYTRA, Items.ENDER_CHEST,
				Items.RECOVERY_COMPASS, Items.SPYGLASS
		);
	}

	private static void initRegistryAddedCallbacks() {
		RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
			if (CompatFlags.FABRIC_SEASONS_LOADED && rl.equals(FabricSeasonsCalendarAccessory.ITEM_ID)) {
				AccessoryUtil.registerEmptyRenderer(item);
			}
			if (CompatFlags.SERENE_SEASONS_LOADED && rl.equals(SereneSeasonsCalendarAccessory.ITEM_ID)) {
				AccessoryUtil.registerEmptyRenderer(item);
			}
		});
	}

	private static void initTagsLoadedEvents() {
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
			if (!tagEventsProcessed) {
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
						AccessoryUtil.registerEmptyRenderer(itemHolder.value())
				);
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(GeneralUtil.LANTERNS).forEach(itemHolder ->
						AccessoryUtil.bindItemToLanternRenderer(itemHolder.value())
				);
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(GeneralUtil.SHULKER_BOXES).forEach(itemHolder ->
						AccessoryUtil.registerEmptyRenderer(itemHolder.value().asItem())
				);
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(GeneralUtil.TOTEMS).forEach(itemHolder ->
						AccessoryUtil.registerEmptyRenderer(itemHolder.value().asItem())
				);
				tagEventsProcessed = true;
			}
		});
	}

	private static void initKeybinds() {
		//? if >= 1.21.10
		KeyMapping.Category.register(Accessorify.id("keys"));
		KeyBindingHelper.registerKeyBinding(ModKeybinds.OPEN_ENDER_CHEST);
		KeyBindingHelper.registerKeyBinding(ModKeybinds.USE_SPYGLASS);
		KeyBindingHelper.registerKeyBinding(ModKeybinds.OPEN_WIDGET);
		ClientTickEvents.END_CLIENT_TICK.register(ModKeybinds::onClientTick);
	}

	private static void initNetworking() {
		//? if > 1.20.1 {
		ClientPlayNetworking.registerGlobalReceiver(
				Payloads.S2CSyncShulkerSlot.TYPE,
				NetworkClientEvents::handleSyncShulkerSlotToClientPayload
		);
		ClientPlayNetworking.registerGlobalReceiver(
				Payloads.S2CSyncArrowSlot.TYPE,
				NetworkClientEvents::handleSyncArrowSlotToClientPayload
		);
		//?} else {
		/*ConfigApiJava.network().registerLenientS2C(
				NetworkConstants.S2C_SYNC_SHULKER_SLOT,
				Payloads.S2CSyncShulkerSlot.class,
				buf -> new Payloads.S2CSyncShulkerSlot(buf.readInt()),
				NetworkClientEvents::handleSyncShulkerSlotToClientPayload
		);
		ConfigApiJava.network().registerLenientS2C(
				NetworkConstants.S2C_SYNC_ARROW_SLOT,
				Payloads.S2CSyncArrowSlot.class,
				buf -> new Payloads.S2CSyncArrowSlot(buf.readInt()),
				NetworkClientEvents::handleSyncArrowSlotToClientPayload
		);
		*///?}
	}
}
//?}
