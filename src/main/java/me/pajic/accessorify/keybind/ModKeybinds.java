package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.gui.ShulkerBoxAccessorySelectionScreen;
import me.pajic.accessorify.network.ModNetworking;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Optional;

public class ModKeybinds {

    private static final KeyMapping USE_SPYGLASS = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.use_spyglass",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_C,
                    "category.accessorify.keybindings"
            )
    );
    private static final KeyMapping OPEN_SHULKER_BOX = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.open_shulker_box",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_B,
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
                if (OPEN_SHULKER_BOX.consumeClick()) {
                    Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(client.player);
                    if (ac.isPresent()) {
                        List<SlotEntryReference> shulkerBoxes = ac.get().getEquipped(ModUtil::isShulkerBox);
                        if (!shulkerBoxes.isEmpty()) {
                            client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                            if (shulkerBoxes.size() == 1) {
                                ClientPlayNetworking.send(new ModNetworking.C2SOpenShulkerBoxPayload(shulkerBoxes.getFirst().reference().slot()));
                            } else {
                                client.setScreen(new ShulkerBoxAccessorySelectionScreen(shulkerBoxes));
                            }
                        }
                    }
                }
            }
        });
    }
}
