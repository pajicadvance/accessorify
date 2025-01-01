package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    private static final KeyMapping USE_SPYGLASS = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.accessorify.use_spyglass",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_C,
                    "category.accessorify.keybindings"
            )
    );

    private static boolean soundPlayed = false;

    public static void initKeybinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (
                    USE_SPYGLASS.isDown() && client.player != null && client.level != null &&
                    AccessoriesCapability.get(client.player).isEquipped(Items.SPYGLASS)
            ) {
                if (!soundPlayed) {
                    client.player.playSound(SoundEvents.SPYGLASS_USE);
                    soundPlayed = true;
                }
                ModUtil.shouldScope = true;
            }
            else {
                if (soundPlayed) {
                    if (client.player != null) client.player.playSound(SoundEvents.SPYGLASS_STOP_USING);
                    soundPlayed = false;
                }
                ModUtil.shouldScope = false;
            }
        });
    }
}
