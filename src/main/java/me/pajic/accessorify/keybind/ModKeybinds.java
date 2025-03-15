package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.gui.ShulkerBoxAccessorySelectionScreen;
import me.pajic.accessorify.network.ModNetworking;
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
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.List;
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
    public static final Lazy<KeyMapping> OPEN_SHULKER_BOX = Lazy.of(() ->
            new KeyMapping(
                    "key.accessorify.open_shulker_box",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_B,
                    "category.accessorify.keybindings"
            )
    );

    private static boolean soundPlayed = false;

    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(USE_SPYGLASS.get());
        event.register(OPEN_SHULKER_BOX.get());
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
            if (OPEN_SHULKER_BOX.get().consumeClick()) {
                Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(client.player);
                if (ac.isPresent()) {
                    List<SlotEntryReference> shulkerBoxes = ac.get().getEquipped(ModUtil::isShulkerBox);
                    if (!shulkerBoxes.isEmpty()) {
                        client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
                        if (shulkerBoxes.size() == 1) {
                            PacketDistributor.sendToServer(new ModNetworking.C2SOpenShulkerBoxPayload(shulkerBoxes.getFirst().reference().slot()));
                        } else {
                            client.setScreen(new ShulkerBoxAccessorySelectionScreen(shulkerBoxes));
                        }
                    }
                }
            }
        }
    }
}
