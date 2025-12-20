package me.pajic.accessorify.platform.neoforge;

//? neoforge {

/*import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.config.SlotMode;
import me.pajic.accessorify.network.NetworkClientEvents;
import me.pajic.accessorify.network.NetworkEvents;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.platform.Platform;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.CompatFlags;
import me.pajic.accessorify.util.GeneralUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;

@Mod(Accessorify.MOD_ID)
@EventBusSubscriber(modid = Accessorify.MOD_ID)
public class NeoforgeEntrypoint {

	private static boolean tagEventsProcessed = false;

	@SubscribeEvent
	private static void onCommonSetup(FMLCommonSetupEvent event) {
		Accessorify.onInitialize();
	}

	@SubscribeEvent
	private static void initCommonResources(AddPackFindersEvent event) {
		event.addPackFinders(
				Accessorify.id(Accessorify.xplat().packPath(Platform.VersionedPackType.DATA)),
				PackType.SERVER_DATA,
				Component.literal("Mod " + Accessorify.xplat().mcVersion() + " Data Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		String path = "resourcepacks/" + Accessorify.xplat().mcVersion().replace(".", "_") + "/" +
				(Accessorify.CONFIG.slotMode.get() == SlotMode.UNIQUE_SLOT ? "unique/" : "default/");
		if (Accessorify.CONFIG.accessorySettings.compassAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "compass"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Compass"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.clockAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "clock"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Clock"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.elytraAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "elytra"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Elytra"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.spyglassAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "spyglass"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Spyglass"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.lanternAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "lantern"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Lanterns"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "totem"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Totem"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.recoveryCompassAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "recoverycompass"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Recovery Compass"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.enderChestAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "enderchest"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Ender Chest"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.shulkerBoxAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "shulkerbox"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Shulker Boxes"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.arrowAccessory.get()) event.addPackFinders(
				Accessorify.id(path + "arrow"),
				PackType.SERVER_DATA,
				Component.literal("Accessorify Arrows"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		if (Accessorify.CONFIG.accessorySettings.calendarAccessory.get()) {
			if (CompatFlags.SERENE_SEASONS_LOADED) event.addPackFinders(
					Accessorify.id(path + "sscalendar"),
					PackType.SERVER_DATA,
					Component.literal("Accessorify Calendar"),
					PackSource.BUILT_IN,
					true,
					Pack.Position.TOP
			);
			if (CompatFlags.FABRIC_SEASONS_LOADED) event.addPackFinders(
					Accessorify.id(path + "fscalendar"),
					PackType.SERVER_DATA,
					Component.literal("Accessorify Calendar"),
					PackSource.BUILT_IN,
					true,
					Pack.Position.TOP
			);
		}
	}

	@SubscribeEvent
	private static void initRegistry(FMLCommonSetupEvent event) {
		AccessoryUtil.registerAccessory(Items.CLOCK, new ClockAccessory());
		AccessoryUtil.registerAccessory(Items.COMPASS, new CompassAccessory());
		AccessoryUtil.registerAccessory(Items.ELYTRA, new ElytraAccessory());
		AccessoryUtil.registerAccessory(Items.ENDER_CHEST, new EnderChestAccessory());
		AccessoryUtil.registerAccessory(Items.RECOVERY_COMPASS, new RecoveryCompassAccessory());
		AccessoryUtil.registerAccessory(Items.SPYGLASS, new SpyglassAccessory());
		AccessoryUtil.registerAccessory(Items.TOTEM_OF_UNDYING, new TotemOfUndyingAccessory());
	}

	@SubscribeEvent
	private static void initRegistryAddedCallbacks(ModifyRegistriesEvent event) {
		event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, item) -> {
			if (CompatFlags.SERENE_SEASONS_LOADED && key.location().equals(SereneSeasonsCalendarAccessory.ITEM_ID)) {
				AccessoryUtil.registerAccessory(item, new SereneSeasonsCalendarAccessory());
			}
		});
	}

	@SubscribeEvent
	private static void initTagsLoadedEvents(TagsUpdatedEvent event) {
		if (!tagEventsProcessed) {
			HolderLookup<Item> lookup = event./^? if < 1.21.10 {^/getRegistryAccess()/^?} else {^//^getLookupProvider()^//^?}^/.lookupOrThrow(Registries.ITEM);
			lookup.getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
					AccessoryUtil.registerAccessory(itemHolder.value(), new ArrowAccessory())
			);
			lookup.getOrThrow(GeneralUtil.LANTERNS).forEach(itemHolder ->
					AccessoryUtil.registerAccessory(itemHolder.value(), new LanternAccessory())
			);
			lookup.getOrThrow(GeneralUtil.SHULKER_BOXES).forEach(blockHolder ->
					AccessoryUtil.registerAccessory(blockHolder.value().asItem(), new ShulkerBoxAccessory())
			);
			tagEventsProcessed = true;
		}
	}

	@SubscribeEvent
	private static void initNetworking(RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar("1");
		registrar.playToServer(
				Payloads.C2SOpenShulkerBoxPayload.TYPE,
				Payloads.C2SOpenShulkerBoxPayload.CODEC,
				(payload, context) ->
						NetworkEvents.openShulkerBox((ServerPlayer) context.player(), payload.index())
		);
		registrar.playToServer(
				Payloads.C2SOpenEnderContainerPayload.TYPE,
				Payloads.C2SOpenEnderContainerPayload.CODEC,
				(payload, context) ->
						NetworkEvents.openEnderContainer((ServerPlayer) context.player())
		);
		registrar.playToServer(
				Payloads.C2SSyncShulkerSlot.TYPE,
				Payloads.C2SSyncShulkerSlot.CODEC,
				(payload, context) ->
						NetworkEvents.syncShulkerSlotToServer((ServerPlayer) context.player(), payload.slot())
		);
		registrar.playToServer(
				Payloads.C2SSyncArrowSlot.TYPE,
				Payloads.C2SSyncArrowSlot.CODEC,
				(payload, context) ->
						NetworkEvents.syncArrowSlotToServer((ServerPlayer) context.player(), payload.slot())
		);
		registrar.playToClient(
				Payloads.S2CSyncShulkerSlot.TYPE,
				Payloads.S2CSyncShulkerSlot.CODEC,
				(payload, context) ->
						NetworkClientEvents.syncShulkerSlotToClient(payload.slot())
		);
		registrar.playToClient(
				Payloads.S2CSyncArrowSlot.TYPE,
				Payloads.S2CSyncArrowSlot.CODEC,
				(payload, context) ->
						NetworkClientEvents.syncArrowSlotToClient(payload.slot())
		);
	}
}
*///?}
