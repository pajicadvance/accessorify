package me.pajic.accessorify.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.Main;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.DeeperDarkerCompat;
import me.pajic.accessorify.compat.friendsandfoes.FriendsAndFoesCompat;
import net.minecraft.world.item.ElytraItem;
//?}

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
        //? if <= 1.21.1 {
        if (Main.DEEPER_DARKER_LOADED) {
            stack = DeeperDarkerCompat.getSoulElytraAccessoryStack(livingEntity);
        }
        //?}
        if (stack.isEmpty()) {
            stack = getAccessoryStack(livingEntity, Items.ELYTRA);
        }
        return stack;
    }

    //? if <= 1.21.1 {
    public static boolean moddedElytraCheck(ItemStack stack, LivingEntity livingEntity, boolean original) {
        if (
                !tryGetElytraAccessory(livingEntity).isEmpty() &&
                !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)
        ) {
            return stack.getItem() instanceof ElytraItem;
        }
        return original;
    }
    //?}

    public static boolean isTotem(ItemStack stack) {
        //? if <= 1.21.1
        return Main.FRIENDS_AND_FOES_LOADED ? FriendsAndFoesCompat.isTotem(stack) : stack.is(Items.TOTEM_OF_UNDYING);
        //? if > 1.21.1
        /*return stack.is(Items.TOTEM_OF_UNDYING);*/
    }
}
