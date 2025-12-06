package me.pajic.accessorify.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.AccessorifyClient;
import me.pajic.accessorify.gui.ContextualSelectionWidget;
import me.pajic.accessorify.network.Payloads;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.ClientUtil;
import me.pajic.accessorify.util.GameplayUtil;
import me.pajic.accessorify.util.NetworkUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class ModKeybinds {

    //? if >= 1.21.10
    public static final KeyMapping.Category MOD_KEYS = new KeyMapping.Category(Accessorify.id("keys"));

    public static final KeyMapping USE_SPYGLASS = new KeyMapping(
			"key.accessorify.use_spyglass",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_C,
			/*? if < 1.21.10 {*//*"category.accessorify.keybindings"*//*?} else {*/MOD_KEYS/*?}*/
	);
    public static final KeyMapping OPEN_WIDGET = new KeyMapping(
			"key.accessorify.open_widget",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_X,
			/*? if < 1.21.10 {*//*"category.accessorify.keybindings"*//*?} else {*/MOD_KEYS/*?}*/
	);
    public static final KeyMapping OPEN_ENDER_CHEST = new KeyMapping(
			"key.accessorify.open_ender_chest",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_V,
			/*? if < 1.21.10 {*//*"category.accessorify.keybindings"*//*?} else {*/MOD_KEYS/*?}*/
	);

    private static boolean soundPlayed = false;

    public static void onClientTick(Minecraft client) {
		if (client.player != null && client.level != null) {
			if (USE_SPYGLASS.isDown() && AccessoryUtil.isAccessoryEquipped(client.player, Items.SPYGLASS)) {
				if (!soundPlayed) {
					client.player.playSound(SoundEvents.SPYGLASS_USE);
					soundPlayed = true;
				}
				ClientUtil.shouldScope = true;
			} else {
				if (soundPlayed) {
					client.player.playSound(SoundEvents.SPYGLASS_STOP_USING);
					soundPlayed = false;
				}
				ClientUtil.shouldScope = false;
			}
			boolean isShulkerWidget = !GameplayUtil.isHoldingProjectileWeapon(client.player);
			if (!AccessorifyClient.CONFIG.widgetSettings.quickSelect.get()) {
				if (OPEN_WIDGET.consumeClick()) {
					if (!ContextualSelectionWidget.widgetOpen)
						ContextualSelectionWidget.widgetOpen = true;
					else {
						if (isShulkerWidget) {
							NetworkUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
							client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
						}
						ContextualSelectionWidget.widgetOpen = false;
					}
				}
			} else if (!OPEN_WIDGET.isDown()) {
				if (ContextualSelectionWidget.widgetOpen) {
					if (isShulkerWidget) {
						NetworkUtil.C2S(new Payloads.C2SOpenShulkerBoxPayload(ModScrollHandler.selectedShulkerSlot));
						client.player.playSound(SoundEvents.SHULKER_BOX_OPEN);
					}
					ContextualSelectionWidget.widgetOpen = false;
				}
			}
			if (OPEN_ENDER_CHEST.consumeClick()) {
				Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(client.player);
				if (ac.isPresent() && ac.get().isEquipped(Items.ENDER_CHEST)) {
					client.player.playSound(SoundEvents.ENDER_CHEST_OPEN);
					NetworkUtil.C2S(new Payloads.C2SOpenEnderContainerPayload());
				}
			}
		}
    }

	public static void onClientStarted(Minecraft client) {
		Options options = client.options;
		if (options.keyLoadHotbarActivator.same(OPEN_WIDGET)) {
			options.keyLoadHotbarActivator.setKey(InputConstants.UNKNOWN);
		}
		if (options.keySaveHotbarActivator.same(USE_SPYGLASS)) {
			options.keySaveHotbarActivator.setKey(InputConstants.UNKNOWN);
		}
	}
}
