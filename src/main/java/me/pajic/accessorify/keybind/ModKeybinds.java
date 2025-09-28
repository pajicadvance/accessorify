package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

@EventBusSubscriber(modid = "accessorify", value = Dist.CLIENT)
public class ModKeybinds {

    public static final Lazy<KeyMapping> USE_SPYGLASS = Lazy.of(() ->
            new KeyMapping(
                    "key.accessorify.use_spyglass",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_C,
                    "category.accessorify.keybindings"
            )
    );
    public static final Lazy<KeyMapping> OPEN_WIDGET = Lazy.of(() ->
            new KeyMapping(
                    "key.accessorify.open_widget",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_X,
                    "category.accessorify.keybindings"
            )
    );
    public static final Lazy<KeyMapping> OPEN_ENDER_CHEST = Lazy.of(() ->
            new KeyMapping(
                    "key.accessorify.open_ender_chest",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    "category.accessorify.keybindings"
            )
    );

    private static boolean soundPlayed = false;

    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(USE_SPYGLASS.get());
        event.register(OPEN_WIDGET.get());
        event.register(OPEN_ENDER_CHEST.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && client.level != null) {
            if (USE_SPYGLASS.get().isDown() && ModUtil.accessoryEquipped(client.player, Items.SPYGLASS)) {
                if (!soundPlayed) {
                    client.player.playSound(SoundEvents.SPYGLASS_USE);
                    soundPlayed = true;
                }
                ModUtil.shouldScope = true;
            } else {
                if (soundPlayed) {
                    client.player.playSound(SoundEvents.SPYGLASS_STOP_USING);
                    soundPlayed = false;
                }
                ModUtil.shouldScope = false;
            }
            boolean isShulkerWidget = !ModUtil.isHoldingProjectileWeapon(client.player);
            if (!ClientMain.CLIENT_CONFIG.widgetSettings.quickSelect.get()) {
                if (OPEN_WIDGET.get().consumeClick()) {
                    if (!ContextualSelectionWidget.widgetOpen)
                        ContextualSelectionWidget.widgetOpen = true;
                    else {
                        if (isShulkerWidget) {
                            MultiVersionUtil.sendToServer(new ModNetworking.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                            client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                        }
                        ContextualSelectionWidget.widgetOpen = false;
                    }
                }
            } else if (!OPEN_WIDGET.get().isDown()) {
                if (ContextualSelectionWidget.widgetOpen) {
                    if (isShulkerWidget) {
                        MultiVersionUtil.sendToServer(new ModNetworking.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                        client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                    }
                    ContextualSelectionWidget.widgetOpen = false;
                }
            }
            if (OPEN_ENDER_CHEST.get().consumeClick()) {
                Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(client.player);
                if (ac.isPresent() && ac.get().isEquipped(Items.ENDER_CHEST)) {
                    client.player.playSound(SoundEvents.ENDER_CHEST_OPEN);
                    MultiVersionUtil.sendToServer(new ModNetworking.C2SOpenEnderContainerPayload());
                }
            }
        }
    }
}
