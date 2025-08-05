package me.pajic.accessorify;

import com.kyanite.deeperdarker.content.DDItems;
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
//? if <= 1.21.1
import me.pajic.accessorify.compat.arselixirum.WitchTotemOfUndyingAccessory;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

@Mod(value = "accessorify", dist = Dist.CLIENT)
public class ClientMain {
    public static final ResourceLocation CLIENT_CONFIG_RL = MultiVersionUtil.fromNamespaceAndPath(Main.MOD_ID, "client_config");
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
        if (Main.CONFIG.accessorySettings.clockAccessory.get()) AccessoriesRendererRegistry.registerNoRenderer(Items.CLOCK);
        if (Main.CONFIG.accessorySettings.compassAccessory.get()) AccessoriesRendererRegistry.registerNoRenderer(Items.COMPASS);
        if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get()) AccessoriesRendererRegistry.registerNoRenderer(Items.RECOVERY_COMPASS);
        if (Main.CONFIG.accessorySettings.spyglassAccessory.get()) AccessoriesRendererRegistry.registerNoRenderer(Items.SPYGLASS);
        if (Main.CONFIG.accessorySettings.lanternAccessory.get()) ModUtil.LANTERNS.forEach(item -> AccessoriesRendererRegistry.registerRenderer(item, LanternAccessoryRenderer::new));
        if (Main.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) AccessoriesRendererRegistry.registerNoRenderer(Items.TOTEM_OF_UNDYING);
        if (Main.CONFIG.accessorySettings.enderChestAccessory.get()) AccessoriesRendererRegistry.registerNoRenderer(Items.ENDER_CHEST);
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            AccessoriesRendererRegistry.registerNoRenderer(Items.ELYTRA);
            //? if <= 1.21.1 {
            if (CompatFlags.DEEPER_DARKER_LOADED) {
                AccessoriesRendererRegistry.registerNoRenderer(DDItems.SOUL_ELYTRA.get());
            }
            //?}
        }
        if (Main.CONFIG.accessorySettings.shulkerBoxAccessory.get()) ModUtil.SHULKER_BOXES.forEach(AccessoriesRendererRegistry::registerNoRenderer);
    }

    @SubscribeEvent
    private void initArrows(TagsUpdatedEvent event) {
        event.getRegistryAccess().lookupOrThrow(Registries.ITEM).getOrThrow(ItemTags.ARROWS).forEach(itemHolder ->
                AccessoriesRendererRegistry.registerNoRenderer(itemHolder.value())
        );
    }
}
