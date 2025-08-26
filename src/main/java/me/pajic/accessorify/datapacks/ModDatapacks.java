package me.pajic.accessorify.datapacks;

import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

public class ModDatapacks {

    public static void init() {
        FabricLoader.getInstance().getModContainer(Main.MOD_ID).ifPresent(modContainer -> {
            String pathPrefix = switch (Main.CONFIG.slotMode.get()) {
                case DEFAULT_SLOT, DEFAULT_SLOT_NO_COPY -> "default/";
                case UNIQUE_SLOT -> "unique/";
            };
            if (Main.CONFIG.accessorySettings.compassAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
                    MultiVersionUtil.withModNamespace(pathPrefix + "compass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.accessorySettings.clockAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
                    MultiVersionUtil.withModNamespace(pathPrefix + "clock"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "elytra"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CompatFlags.DEEPER_DARKER_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "soulelytra"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (Main.CONFIG.accessorySettings.spyglassAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
                    MultiVersionUtil.withModNamespace(pathPrefix + "spyglass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.accessorySettings.lanternAccessory.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "lantern"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CompatFlags.ADDITIONAL_LANTERNS_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "additionallanterns"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "totem"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CompatFlags.FRIENDS_AND_FOES_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "fnftotems"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CompatFlags.ARS_ELIXIRUM_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "witchtotem"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
                    MultiVersionUtil.withModNamespace(pathPrefix + "recoverycompass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.accessorySettings.enderChestAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
                    MultiVersionUtil.withModNamespace(pathPrefix + "enderchest"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "shulkerbox"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CompatFlags.REINFORCED_SHULKERS_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "reinfshulker"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (Main.CONFIG.accessorySettings.arrowAccessory.get()) ResourceManagerHelper.registerBuiltinResourcePack(
                    MultiVersionUtil.withModNamespace(pathPrefix + "arrow"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.accessorySettings.calendarAccessory.get()) {
                if (CompatFlags.SERENE_SEASONS_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "sscalendar"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CompatFlags.FABRIC_SEASONS_LOADED && CompatFlags.FABRIC_SEASONS_EXTRAS_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        MultiVersionUtil.withModNamespace(pathPrefix + "fscalendar"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
        });
    }
}
