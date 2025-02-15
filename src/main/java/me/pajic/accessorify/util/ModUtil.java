package me.pajic.accessorify.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.compat.FabricSeasonsCompat;
import me.pajic.accessorify.util.compat.SereneSeasonsCompat;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if <= 1.21.1 {
import me.pajic.accessorify.compat.deeperdarker.DeeperDarkerCompat;
import me.pajic.accessorify.compat.friendsandfoes.FriendsAndFoesCompat;
import net.minecraft.world.item.ElytraItem;
//?}

import java.util.List;
import java.util.Optional;

public class ModUtil {

    public static boolean shouldScope = false;
    public static float zoomModifier = 1.0F;

    public static final List<Item> SHULKER_BOXES = List.of(
            Items.SHULKER_BOX,
            Items.WHITE_SHULKER_BOX,
            Items.ORANGE_SHULKER_BOX,
            Items.MAGENTA_SHULKER_BOX,
            Items.LIGHT_BLUE_SHULKER_BOX,
            Items.YELLOW_SHULKER_BOX,
            Items.LIME_SHULKER_BOX,
            Items.PINK_SHULKER_BOX,
            Items.GRAY_SHULKER_BOX,
            Items.LIGHT_GRAY_SHULKER_BOX,
            Items.CYAN_SHULKER_BOX,
            Items.PURPLE_SHULKER_BOX,
            Items.BLUE_SHULKER_BOX,
            Items.BROWN_SHULKER_BOX,
            Items.GREEN_SHULKER_BOX,
            Items.RED_SHULKER_BOX,
            Items.BLACK_SHULKER_BOX
    );

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

    public static boolean accessoryEquipped(LivingEntity entity, Item item) {
        Optional<AccessoriesCapability> playerCapability = AccessoriesCapability.getOptionally(entity);
        return playerCapability.map(accessoriesCapability -> accessoriesCapability.isEquipped(item)).orElse(false);
    }

    public static boolean calendarAccessoryEquipped(LivingEntity entity) {
        if (Main.SERENE_SEASONS_LOADED) return SereneSeasonsCompat.calendarAccessoryEquipped(entity);
        else if (Main.FABRIC_SEASONS_LOADED && Main.FABRIC_SEASONS_EXTRAS_LOADED) return FabricSeasonsCompat.calendarAccessoryEquipped(entity);
        return false;
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

    public static boolean isShulkerBox(ItemStack stack) {
        return SHULKER_BOXES.stream().anyMatch(stack::is);
    }

    public static boolean calendarUsedForSeasonInfo() {
        return Main.CONFIG.calendarAccessory() && (Main.SERENE_SEASONS_LOADED || (Main.FABRIC_SEASONS_LOADED && Main.FABRIC_SEASONS_EXTRAS_LOADED));
    }
}
