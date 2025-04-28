package me.pajic.accessorify.datapacks;

import me.pajic.accessorify.Main;
import me.pajic.accessorify.config.ModCommonConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class ModDatapacks {

    @SubscribeEvent
    public static void registerDatapacks(AddPackFindersEvent event) {
        if (ModCommonConfig.compassAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "compass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Compass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.clockAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "clock"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Clock"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.elytraAccessory) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "elytra"),
                    PackType.SERVER_DATA,
                    Component.literal("Accessorify Elytra"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
            if (Main.DEEPER_DARKER_LOADED) {
                event.addPackFinders(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "soulelytra"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify Soul Elytra"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
        }
        if (ModCommonConfig.spyglassAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "spyglass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Spyglass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.totemOfUndyingAccessory) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "totem"),
                    PackType.SERVER_DATA,
                    Component.literal("Accessorify Totem of Undying"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
            if (Main.FRIENDS_AND_FOES_LOADED) {
                event.addPackFinders(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "fnftotems"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify FNF Totems"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
            if (Main.ARS_ELIXIRUM_LOADED) {
                event.addPackFinders(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "witchtotem"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify Witch Totems"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
        }
        if (ModCommonConfig.enderChestAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "enderchest"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Ender Chest"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.recoveryCompassAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "recoverycompass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Recovery Compass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.shulkerBoxAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "shulkerbox"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Shulker Box"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.arrowAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "arrow"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Arrows"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.calendarAccessory && Main.SERENE_SEASONS_LOADED) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "sscalendar"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Calendar"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
    }
}
