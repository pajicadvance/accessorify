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
                ResourceLocation.fromNamespaceAndPath("accessorify", "compass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Compass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.clockAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath("accessorify", "clock"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Clock"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.elytraAccessory) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath("accessorify", "elytra"),
                    PackType.SERVER_DATA,
                    Component.literal("Accessorify Elytra"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
            //? if <= 1.21.1 {
            if (Main.DEEPER_DARKER_LOADED) {
                event.addPackFinders(
                        ResourceLocation.fromNamespaceAndPath("accessorify", "soulelytra"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify Soul Elytra"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
            //?}
        }
        if (ModCommonConfig.spyglassAccessory) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath("accessorify", "spyglass"),
                PackType.SERVER_DATA,
                Component.literal("Accessorify Spyglass"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        if (ModCommonConfig.totemOfUndyingAccessory) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath("accessorify", "totem"),
                    PackType.SERVER_DATA,
                    Component.literal("Accessorify Totem"),
                    PackSource.BUILT_IN,
                    true,
                    Pack.Position.TOP
            );
            //? if <= 1.21.1 {
            if (Main.FRIENDS_AND_FOES_LOADED) {
                event.addPackFinders(
                        ResourceLocation.fromNamespaceAndPath("accessorify", "fnftotems"),
                        PackType.SERVER_DATA,
                        Component.literal("Accessorify FNF Totems"),
                        PackSource.BUILT_IN,
                        true,
                        Pack.Position.TOP
                );
            }
            //?}
        }
    }
}
