package me.pajic.accessorify.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.compat.deeperdarker.DeeperDarkerCompat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class ModUtil {

    public static boolean shouldScope = false;

    public static ItemStack getAccessoryStack(LivingEntity entity, Item item) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(entity);
        if (ac.isPresent() && ac.get().isEquipped(item)) {
            SlotEntryReference itemRef = ac.get().getFirstEquipped(item);
            if (itemRef != null) {
                return ac.get().getContainer(itemRef.reference().type()).getAccessories().getItem(0);
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean accessoryEquipped(Player player, Item item) {
        Optional<AccessoriesCapability> playerCapability = AccessoriesCapability.getOptionally(player);
        return playerCapability.map(accessoriesCapability -> accessoriesCapability.isEquipped(item)).orElse(false);
    }

    public static ItemStack tryGetElytraAccessory(LivingEntity livingEntity) {
        ItemStack stack = ItemStack.EMPTY;
        if (Main.DEEPER_DARKER_LOADED) {
            stack = DeeperDarkerCompat.getSoulElytraAccessoryStack(livingEntity);
        }
        if (stack.isEmpty()) {
            stack = getAccessoryStack(livingEntity, Items.ELYTRA);
        }
        return stack;
    }
}
