package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class ModKeybinds {

    public static final KeyMapping USE_SPYGLASS = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.use_spyglass",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_C,
                    "category.accessorify.keybindings"
            )
    );
    public static final KeyMapping OPEN_WIDGET = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.open_widget",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_X,
                    "category.accessorify.keybindings"
            )
    );
    public static final KeyMapping OPEN_ENDER_CHEST = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.open_ender_chest",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    "category.accessorify.keybindings"
            )
    );

    private static boolean soundPlayed = false;

    public static void initKeybinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.level != null) {
                if (USE_SPYGLASS.isDown() && ModUtil.accessoryEquipped(client.player, Items.SPYGLASS)) {
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
                    if (OPEN_WIDGET.consumeClick()) {
                        if (!ContextualSelectionWidget.widgetOpen)
                            ContextualSelectionWidget.widgetOpen = true;
                        else {
                            if (isShulkerWidget) {
                                MultiVersionUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                                client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                            }
                            ContextualSelectionWidget.widgetOpen = false;
                        }
                    }
                } else if (!OPEN_WIDGET.isDown()) {
                    if (ContextualSelectionWidget.widgetOpen) {
                        if (isShulkerWidget) {
                            MultiVersionUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                            client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                        }
                        ContextualSelectionWidget.widgetOpen = false;
                    }
                }
                if (OPEN_ENDER_CHEST.consumeClick()) {
                    Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(client.player);
                    if (ac.isPresent() && ac.get().isEquipped(Items.ENDER_CHEST)) {
                        client.player.playSound(SoundEvents.ENDER_CHEST_OPEN);
                        MultiVersionUtil.C2S(new Payloads.C2SOpenEnderContainerPayload());
                    }
                }
            }
        });
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            Options options = client.options;
            if (options.keyLoadHotbarActivator.matches(KeyBindingHelper.getBoundKeyOf(OPEN_WIDGET).getValue(), -1)) {
                options.keyLoadHotbarActivator.setKey(InputConstants.UNKNOWN);
            }
            if (options.keySaveHotbarActivator.matches(KeyBindingHelper.getBoundKeyOf(USE_SPYGLASS).getValue(), -1)) {
                options.keySaveHotbarActivator.setKey(InputConstants.UNKNOWN);
            }
        });
    }
}
