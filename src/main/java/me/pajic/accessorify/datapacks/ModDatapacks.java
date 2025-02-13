package me.pajic.accessorify.datapacks;

import me.pajic.accessorify.Main;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class ModDatapacks {

    public static void init() {
        FabricLoader.getInstance().getModContainer(Main.MOD_ID).ifPresent(modContainer -> {
            if (Main.CONFIG.compassAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "compass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.clockAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "clock"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.elytraAccessory()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "elytra"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                //? if <= 1.21.1 {
                if (Main.DEEPER_DARKER_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "soulelytra"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                //?}
            }
            if (Main.CONFIG.spyglassAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "spyglass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.totemOfUndyingAccessory()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "totem"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                //? if <= 1.21.1 {
                if (Main.FRIENDS_AND_FOES_LOADED) ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "fnftotems"),
                        modContainer, ResourcePackActivationType.ALWAYS_ENABLED
                );
                //?}
            }
            if (Main.CONFIG.recoveryCompassAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "recoverycompass"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
            if (Main.CONFIG.shulkerBoxAccessory()) ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "shulkerbox"),
                    modContainer, ResourcePackActivationType.ALWAYS_ENABLED
            );
        });
    }
}
