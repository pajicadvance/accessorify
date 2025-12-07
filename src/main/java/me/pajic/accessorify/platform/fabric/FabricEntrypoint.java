package me.pajic.accessorify.platform.fabric;

//? fabric {

import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.FabricSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.config.SlotMode;
import me.pajic.accessorify.network.NetworkEvents;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.platform.Platform;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.CompatFlags;
import me.pajic.accessorify.util.GeneralUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
//? if > 1.20.1 {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//?} else {
/*import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.accessorify.network.NetworkConstants;
*///?}

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	private static boolean tagEventsProcessed = false;

	@Override
	public void onInitialize() {
		Accessorify.onInitialize();
		initConditionalCommonResources();
		initRegistry();
		initRegistryAddedCallbacks();
		initTagsLoadedEvents();
		initNetworking();
	}

	private static void initConditionalCommonResources() {
		FabricLoader.getInstance().getModContainer(Accessorify.MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(Accessorify.xplat().packPath(Platform.VersionedPackType.DATA)),
					modContainer,
					ResourcePackActivationType.ALWAYS_ENABLED
			);
			String path = Accessorify.xplat().mcVersion().replace(".", "_") + "/" +
					(Accessorify.CONFIG.slotMode.get() == SlotMode.UNIQUE_SLOT ? "unique/" : "default/");
			if (Accessorify.CONFIG.accessorySettings.compassAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(path + "compass"),
					modContainer, ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (Accessorify.CONFIG.accessorySettings.clockAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(path + "clock"),
					modContainer, ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (Accessorify.CONFIG.accessorySettings.elytraAccessory.get()) {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(path + "elytra"),
						modContainer, ResourcePackActivationType.ALWAYS_ENABLED
				);
			}
			if (Accessorify.CONFIG.accessorySettings.spyglassAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(path + "spyglass"),
					modContainer, ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (Accessorify.CONFIG.accessorySettings.lanternAccessory.get()) {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(path + "lantern"),
						modContainer, ResourcePackActivationType.ALWAYS_ENABLED
				);
			}
			if (Accessorify.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(path + "totem"),
						modContainer, ResourcePackActivationType.ALWAYS_ENABLED
				);
			}
			if (Accessorify.CONFIG.accessorySettings.recoveryCompassAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(path + "recoverycompass"),
					modContainer, ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (Accessorify.CONFIG.accessorySettings.enderChestAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(path + "enderchest"),
					modContainer, ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (Accessorify.CONFIG.accessorySettings.shulkerBoxAccessory.get()) {
				ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(path + "shulkerbox"),
						modContainer, ResourcePackActivationType.ALWAYS_ENABLED
				);
			}
			if (Accessorify.CONFIG.accessorySettings.arrowAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					Accessorify.id(path + "arrow"),
					modContainer, ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (Accessorify.CONFIG.accessorySettings.calendarAccessory.get()) {
				if (CompatFlags.SERENE_SEASONS_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(path + "sscalendar"),
						modContainer, ResourcePackActivationType.ALWAYS_ENABLED
				);
				if (CompatFlags.FABRIC_SEASONS_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
						Accessorify.id(path + "fscalendar"),
						modContainer, ResourcePackActivationType.ALWAYS_ENABLED
				);
			}
		});
	}

	private static void initRegistry() {
		AccessoryUtil.registerAccessory(Items.CLOCK, new ClockAccessory());
		AccessoryUtil.registerAccessory(Items.COMPASS, new CompassAccessory());
		AccessoryUtil.registerAccessory(Items.ELYTRA, new ElytraAccessory());
		AccessoryUtil.registerAccessory(Items.ENDER_CHEST, new EnderChestAccessory());
		AccessoryUtil.registerAccessory(Items.RECOVERY_COMPASS, new RecoveryCompassAccessory());
		AccessoryUtil.registerAccessory(Items.SPYGLASS, new SpyglassAccessory());
	}

	private static void initRegistryAddedCallbacks() {
		RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((i, rl, item) -> {
			if (CompatFlags.FABRIC_SEASONS_LOADED && rl.equals(FabricSeasonsCalendarAccessory.ITEM_ID)) {
				AccessoryUtil.registerAccessory(item, new FabricSeasonsCalendarAccessory());
			}
			if (CompatFlags.SERENE_SEASONS_LOADED && rl.equals(SereneSeasonsCalendarAccessory.ITEM_ID)) {
				AccessoryUtil.registerAccessory(item, new SereneSeasonsCalendarAccessory());
			}
		});
	}

	private static void initTagsLoadedEvents() {
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
			if (!tagEventsProcessed) {
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
						AccessoryUtil.registerAccessory(itemHolder.value(), new ArrowAccessory())
				);
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(GeneralUtil.LANTERNS).forEach(itemHolder ->
						AccessoryUtil.registerAccessory(itemHolder.value(), new LanternAccessory())
				);
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(GeneralUtil.SHULKER_BOXES).forEach(itemHolder ->
						AccessoryUtil.registerAccessory(itemHolder.value().asItem(), new ShulkerBoxAccessory())
				);
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(GeneralUtil.TOTEMS).forEach(itemHolder ->
						AccessoryUtil.registerAccessory(itemHolder.value().asItem(), new TotemOfUndyingAccessory())
				);
				tagEventsProcessed = true;
			}
		});
	}

	private static void initNetworking() {
		//? if > 1.20.1 {
		PayloadTypeRegistry.playC2S().register(Payloads.C2SOpenShulkerBoxPayload.TYPE, Payloads.C2SOpenShulkerBoxPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(Payloads.C2SOpenEnderContainerPayload.TYPE, Payloads.C2SOpenEnderContainerPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(Payloads.C2SSyncShulkerSlot.TYPE, Payloads.C2SSyncShulkerSlot.CODEC);
		PayloadTypeRegistry.playS2C().register(Payloads.S2CSyncShulkerSlot.TYPE, Payloads.S2CSyncShulkerSlot.CODEC);
		PayloadTypeRegistry.playC2S().register(Payloads.C2SSyncArrowSlot.TYPE, Payloads.C2SSyncArrowSlot.CODEC);
		PayloadTypeRegistry.playS2C().register(Payloads.S2CSyncArrowSlot.TYPE, Payloads.S2CSyncArrowSlot.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(
				Payloads.C2SOpenShulkerBoxPayload.TYPE,
				NetworkEvents::handleOpenShulkerBoxPayload
		);
		ServerPlayNetworking.registerGlobalReceiver(
				Payloads.C2SOpenEnderContainerPayload.TYPE,
				NetworkEvents::handleOpenEnderContainerPayload
		);
		ServerPlayNetworking.registerGlobalReceiver(
				Payloads.C2SSyncShulkerSlot.TYPE,
				NetworkEvents::handleSyncShulkerSlotToServerPayload
		);
		ServerPlayNetworking.registerGlobalReceiver(
				Payloads.C2SSyncArrowSlot.TYPE,
				NetworkEvents::handleSyncArrowSlotToServerPayload
		);
		//?} else {
		/*ConfigApiJava.network().registerLenientC2S(
				NetworkConstants.OPEN_SHULKER_BOX,
				Payloads.C2SOpenShulkerBoxPayload.class,
				buf -> new Payloads.C2SOpenShulkerBoxPayload(buf.readInt()),
				NetworkEvents::handleOpenShulkerBoxPayload
		);
		ConfigApiJava.network().registerLenientC2S(
				NetworkConstants.OPEN_ENDER_CONTAINER,
				Payloads.C2SOpenEnderContainerPayload.class,
				buf -> new Payloads.C2SOpenEnderContainerPayload(),
				NetworkEvents::handleOpenEnderContainerPayload
		);
		ConfigApiJava.network().registerLenientC2S(
				NetworkConstants.C2S_SYNC_SHULKER_SLOT,
				Payloads.C2SSyncShulkerSlot.class,
				buf -> new Payloads.C2SSyncShulkerSlot(buf.readInt()),
				NetworkEvents::handleSyncShulkerSlotToServerPayload
		);
		ConfigApiJava.network().registerLenientC2S(
				NetworkConstants.C2S_SYNC_ARROW_SLOT,
				Payloads.C2SSyncArrowSlot.class,
				buf -> new Payloads.C2SSyncArrowSlot(buf.readInt()),
				NetworkEvents::handleSyncArrowSlotToServerPayload
		);
		*///?}
	}
}
//?}
