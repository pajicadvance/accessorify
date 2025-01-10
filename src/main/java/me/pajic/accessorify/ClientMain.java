package me.pajic.accessorify;

import com.kyanite.deeperdarker.content.DDItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.keybind.ModKeybinds;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = "accessorify", dist = Dist.CLIENT)
public class ClientMain {

    public ClientMain(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ModClientConfig.CLIENT_SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        modEventBus.addListener(this::onInitialize);
        modEventBus.addListener(ModKeybinds::registerKeybinds);
    }

    public void onInitialize(FMLClientSetupEvent event) {
        if (ModCommonConfig.compassAccessory || ModCommonConfig.clockAccessory) NeoForge.EVENT_BUS.addListener(InfoOverlays::renderInfoOverlays);
        if (ModCommonConfig.clockAccessory) AccessoriesRendererRegistry.registerNoRenderer(Items.CLOCK);
        if (ModCommonConfig.compassAccessory) AccessoriesRendererRegistry.registerNoRenderer(Items.COMPASS);
        if (ModCommonConfig.elytraAccessory) {
            AccessoriesRendererRegistry.registerNoRenderer(Items.ELYTRA);
            //? if <= 1.21.1 {
            if (Main.DEEPER_DARKER_LOADED) {
                AccessoriesRendererRegistry.registerNoRenderer(DDItems.SOUL_ELYTRA.get());
            }
            //?}
        }
    }
}
