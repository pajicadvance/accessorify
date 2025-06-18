package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.gui.ArrowSelectionWidget;
import me.pajic.accessorify.gui.ShulkerBoxSelectionWidget;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.ModUtil;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
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
    public static final KeyMapping OPEN_SHULKER_BOX = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.open_shulker_box",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_B,
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
                if (!ClientMain.CLIENT_CONFIG.widgetSettings.shulkerQuickSelect.get()) {
                    if (OPEN_SHULKER_BOX.consumeClick()) {
                        if (!ShulkerBoxSelectionWidget.widgetOpen && !ArrowSelectionWidget.widgetOpen)
                            ShulkerBoxSelectionWidget.widgetOpen = true;
                        else {
                            MultiVersionUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                            client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                            ShulkerBoxSelectionWidget.widgetOpen = false;
                        }
                    }
                } else if (!OPEN_SHULKER_BOX.isDown()) {
                    if (ShulkerBoxSelectionWidget.widgetOpen) {
                        MultiVersionUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
                        client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                        ShulkerBoxSelectionWidget.widgetOpen = false;
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
    }
}
