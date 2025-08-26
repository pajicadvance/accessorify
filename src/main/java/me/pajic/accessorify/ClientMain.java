package me.pajic.accessorify;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.accessorify.accessories.compat.SereneSeasonsCalendarAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfFreezingAccessory;
import me.pajic.accessorify.accessories.compat.TotemOfIllusionAccessory;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import me.pajic.accessorify.keybind.ModKeybinds;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
import me.pajic.accessorify.compat.deeperdarker.DeeperDarkerCompat;
//?}
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@Mod(value = "accessorify", dist = Dist.CLIENT)
public class ClientMain {
    public static final ResourceLocation CLIENT_CONFIG_RL = MultiVersionUtil.withModNamespace("client_config");
    public static ModClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);

    public ClientMain(IEventBus modEventBus) {
        modEventBus.addListener(this::onInitialize);
        if (Main.CONFIG.accessorySettings.arrowAccessory.get()) NeoForge.EVENT_BUS.addListener(this::initArrows);
        modEventBus.addListener(SereneSeasonsCalendarAccessory::clientInit);
        modEventBus.addListener(TotemOfFreezingAccessory::clientInit);
        modEventBus.addListener(TotemOfIllusionAccessory::clientInit);
        //? if <= 1.21.1
        modEventBus.addListener(WitchTotemOfUndyingAccessory::clientInit);
        modEventBus.addListener(ModKeybinds::registerKeybinds);
    }

    public void onInitialize(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(InfoOverlays::renderInfoOverlays);
        NeoForge.EVENT_BUS.addListener(ContextualSelectionWidget::renderContextualSelectionWidget);
        if (Main.CONFIG.accessorySettings.clockAccessory.get()) MultiVersionUtil.noRenderer(Items.CLOCK);
        if (Main.CONFIG.accessorySettings.compassAccessory.get()) MultiVersionUtil.noRenderer(Items.COMPASS);
        if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get()) MultiVersionUtil.noRenderer(Items.RECOVERY_COMPASS);
        if (Main.CONFIG.accessorySettings.spyglassAccessory.get()) MultiVersionUtil.noRenderer(Items.SPYGLASS);
        if (Main.CONFIG.accessorySettings.lanternAccessory.get()) {
            //? if > 1.21.4 {
            /*AccessoriesRendererRegistry.registerRenderer(MultiVersionUtil.withModNamespace("lantern_renderer"), LanternAccessoryRenderer::new);
            ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.bindItemToRenderer(item, MultiVersionUtil.withModNamespace("lantern_renderer")));
            *///?}
            //? if <= 1.21.4
            ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.registerRenderer(item, LanternAccessoryRenderer::new));
        }
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) MultiVersionUtil.noRenderer(Items.TOTEM_OF_UNDYING);
        if (Main.CONFIG.accessorySettings.enderChestAccessory.get()) MultiVersionUtil.noRenderer(Items.ENDER_CHEST);
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            MultiVersionUtil.noRenderer(Items.ELYTRA);
            //? if <= 1.21.1 {
            if (CompatFlags.DEEPER_DARKER_LOADED) {
                DeeperDarkerCompat.init();
            }
            //?}
        }
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get()) ModUtil.SHULKER_BOXES.forEach(MultiVersionUtil::noRenderer);
    }

    @SubscribeEvent
    private void initArrows(TagsUpdatedEvent event) {
        //? if <= 1.21.1
        event.getRegistryAccess()
        //? if >= 1.21.4
        /*event.getLookupProvider()*/
                .lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
                        MultiVersionUtil.noRenderer(itemHolder.value())
        );
    }
}
