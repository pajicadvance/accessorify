package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.util.ModUtil;
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

    private static boolean soundPlayed = false;

    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(USE_SPYGLASS.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (
                USE_SPYGLASS.get().isDown() && client.player != null && client.level != null &&
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
    }
}
