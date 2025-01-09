package me.pajic.accessorify.datapacks;

import me.pajic.accessorify.Main;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class ModDatapacks {

    public static void init() {
        FabricLoader.getInstance().getModContainer("accessorify").ifPresent(modContainer -> {
            if (Main.CONFIG.compassAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath("accessorify", "compass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.clockAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath("accessorify", "clock"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.elytraAccessory()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath("accessorify", "elytra"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (Main.DEEPER_DARKER_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath("accessorify", "soulelytra"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (Main.CONFIG.spyglassAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath("accessorify", "spyglass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.totemOfUndyingAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath("accessorify", "totem"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
        });
    }
}
