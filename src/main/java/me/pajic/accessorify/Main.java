package me.pajic.accessorify;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.accessorify.accessories.*;
import me.pajic.accessorify.accessories.compat.AdditionalLanternAccessory;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfFreezingAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfIllusionAccessory;
import me.pajic.accessorify.datapacks.ModDatapacks;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.SoulElytraAccessory;
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
import me.pajic.accessorify.compat.netheriteextras.TotemOfNeverdyingAccessory;
//?}

@Mod("accessorify")
public class Main {
    public static final String MOD_ID = "accessorify";
    public static final ResourceLocation CONFIG_RL = MultiVersionUtil.withModNamespace("config");
    public static me.pajic.accessorify.config.ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(me.pajic.accessorify.config.ModConfig::new);

    public Main(IEventBus modEventBus) {
        modEventBus.addListener(ModDatapacks::registerDatapacks);
        modEventBus.addListener(ModNetworking::init);
        modEventBus.addListener(this::onInitialize);
        if (Main.CONFIG.accessorySettings.arrowAccessory.get()) NeoForge.EVENT_BUS.addListener(ArrowAccessory::init);
        if (Main.CONFIG.accessorySettings.lanternAccessory.get()) NeoForge.EVENT_BUS.addListener(LanternAccessory::init);
        modEventBus.addListener(AdditionalLanternAccessory::init);
        modEventBus.addListener(SereneSeasonsCalendarAccessory::init);
        modEventBus.addListener(TotemOfFreezingAccessory::init);
        modEventBus.addListener(TotemOfIllusionAccessory::init);
        //? if <= 1.21.1 {
        modEventBus.addListener(WitchTotemOfUndyingAccessory::init);
        modEventBus.addListener(TotemOfNeverdyingAccessory::init);
        //?}
    }

    public void onInitialize(FMLCommonSetupEvent event) {
        if (Main.CONFIG.accessorySettings.clockAccessory.get()) ClockAccessory.init();
        if (Main.CONFIG.accessorySettings.compassAccessory.get()) CompassAccessory.init();
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            ElytraAccessory.init();
            //? if <= 1.21.1 {
            if (CompatFlags.DEEPER_DARKER_LOADED) {
                SoulElytraAccessory.init();
            }
            //?}
        }
        if (Main.CONFIG.accessorySettings.spyglassAccessory.get()) SpyglassAccessory.init();
        //? if < 1.21.10 {
        if (Main.CONFIG.accessorySettings.lanternAccessory.get()) {
            SoulLanternAccessory.init();
        }
        //?}
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) TotemOfUndyingAccessory.init();
        if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get()) RecoveryCompassAccessory.init();
        if (Main.CONFIG.accessorySettings.enderChestAccessory.get()) EnderChestAccessory.init();
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get()) ShulkerBoxAccessory.init();
    }
}
