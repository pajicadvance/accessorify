package me.pajic.accessorify;

import me.pajic.accessorify.accessories.*;

import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfFreezingAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfIllusionAccessory;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.config.ModServerConfig;
import me.pajic.accessorify.datapacks.ModDatapacks;
import me.pajic.accessorify.network.ModNetworking;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
//?}

@Mod("accessorify")
public class Main {

    public static final String MOD_ID = "accessorify";
    public static final boolean DEEPER_DARKER_LOADED = ModList.get().isLoaded("deeperdarker");
    public static final boolean RAISED_LOADED = ModList.get().isLoaded("raised");
    public static final boolean SERENE_SEASONS_LOADED = ModList.get().isLoaded("sereneseasons");
    public static final boolean FRIENDS_AND_FOES_LOADED = ModList.get().isLoaded("friendsandfoes");
    public static final boolean ARS_ELIXIRUM_LOADED = ModList.get().isLoaded("elixirum");

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, ModCommonConfig.COMMON_SPEC);
        modContainer.registerConfig(ModConfig.Type.SERVER, ModServerConfig.SERVER_SPEC);
        modEventBus.addListener(ModDatapacks::registerDatapacks);
        modEventBus.addListener(ModNetworking::init);
        modEventBus.addListener(this::onInitialize);
        modEventBus.addListener(SereneSeasonsCalendarAccessory::init);
        modEventBus.addListener(TotemOfFreezingAccessory::init);
        modEventBus.addListener(TotemOfIllusionAccessory::init);
        //? if <= 1.21.1
        modEventBus.addListener(WitchTotemOfUndyingAccessory::init);
    }

    public void onInitialize(FMLCommonSetupEvent event) {
        if (ModCommonConfig.clockAccessory) ClockAccessory.init();
        if (ModCommonConfig.compassAccessory) CompassAccessory.init();
        if (ModCommonConfig.elytraAccessory) {
            ElytraAccessory.init();
            //? if <= 1.21.1 {
            if (DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.init();
            }
            //?}
        }
        if (ModCommonConfig.spyglassAccessory) SpyglassAccessory.init();
        if (ModCommonConfig.totemOfUndyingAccessory) TotemOfUndyingAccessory.init();
        if (ModCommonConfig.recoveryCompassAccessory) RecoveryCompassAccessory.init();
        if (ModCommonConfig.enderChestAccessory) EnderChestAccessory.init();
        if (ModCommonConfig.shulkerBoxAccessory) ShulkerBoxAccessory.init();
        if (ModCommonConfig.arrowAccessory) ArrowAccessory.init();
    }
}
