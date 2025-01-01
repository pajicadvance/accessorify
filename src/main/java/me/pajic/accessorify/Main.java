package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.config.ModServerConfig;
import me.pajic.accessorify.datapacks.ModDatapacks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("accessorify")
public class Main {

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ModCommonConfig.COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, ModServerConfig.SERVER_SPEC);
        modEventBus.addListener(ModDatapacks::registerDatapacks);
        modEventBus.addListener(this::onInitialize);
    }

    public void onInitialize(FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("accessories")) {
            if (ModCommonConfig.clockAccessory) ClockAccessory.init();
            if (ModCommonConfig.compassAccessory) CompassAccessory.init();
            if (ModCommonConfig.elytraAccessory) ElytraAccessory.init();
            if (ModCommonConfig.spyglassAccessory) SpyglassAccessory.init();
            if (ModCommonConfig.totemOfUndyingAccessory) TotemOfUndyingAccessory.init();
        }
    }
}
