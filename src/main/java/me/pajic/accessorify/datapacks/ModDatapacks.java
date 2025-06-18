package me.pajic.accessorify.datapacks;

import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class ModDatapacks {

    @SubscribeEvent
    public static void registerDatapacks(AddPackFindersEvent event) {
        String pathPrefix = switch (Main.CONFIG.slotMode.get()) {
            case DEFAULT_SLOT, DEFAULT_SLOT_NO_COPY -> "default/";
            case UNIQUE_SLOT -> "unique/";
        };
        if (Main.CONFIG.accessorySettings.compassAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "compass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Compass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.clockAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "clock"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Clock"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            event.addPackFinders(
                    MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "elytra"),
                    PackType.SERVER_DATA,
                    Component.literal("Accessorify Elytra"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
            if (CompatFlags.DEEPER_DARKER_LOADED) {
                event.addPackFinders(
                        MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "soulelytra"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify Soul Elytra"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
        }
        if (Main.CONFIG.accessorySettings.spyglassAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "spyglass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Spyglass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
            event.addPackFinders(
                    MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "totem"),
                    PackType.SERVER_DATA,
                    Component.literal("Accessorify Totem of Undying"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
            if (CompatFlags.FRIENDS_AND_FOES_LOADED) {
                event.addPackFinders(
                        MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "fnftotems"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify FNF Totems"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
            if (CompatFlags.ARS_ELIXIRUM_LOADED) {
                event.addPackFinders(
                        MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "witchtotem"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify Witch Totems"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
        }
        if (Main.CONFIG.accessorySettings.enderChestAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "enderchest"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Ender Chest"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "recoverycompass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Recovery Compass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "shulkerbox"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Shulker Box"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.arrowAccessory.get()) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "arrow"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Arrows"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (Main.CONFIG.accessorySettings.calendarAccessory.get() && CompatFlags.SERENE_SEASONS_LOADED) event.addPackFinders(
                MultiVersionUtil.fromNamespaceAndPath(pathPrefix + "sscalendar"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Calendar"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
    }
}
