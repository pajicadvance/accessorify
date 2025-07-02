package me.pajic.accessorify.util;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import it.unimi.dsi.fastutil.booleans.BooleanObjectImmutablePair;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.compat.CompatFlags;
import me.pajic.accessorify.util.compat.FabricSeasonsCompat;
import me.pajic.accessorify.util.compat.FriendsAndFoesCompat;
import me.pajic.accessorify.util.compat.SereneSeasonsCompat;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
//? if 1.21.1
import me.pajic.accessorify.compat.arselixirum.ArsElixirumCompat;
//? if <= 1.21.1
import me.pajic.accessorify.compat.deeperdarker.DeeperDarkerCompat;
//? if >= 1.21.1
import net.minecraft.core.component.DataComponents;

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

    public static final List<Item> ARROWS = List.of(
            Items.ARROW,
            Items.TIPPED_ARROW,
            Items.SPECTRAL_ARROW
    );

    public static final List<Item> LANTERNS = List.of(
            Items.LANTERN,
            Items.SOUL_LANTERN
    );

    public static BooleanObjectImmutablePair<ItemStack> getAccessoryStack(LivingEntity entity, Item item) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(entity);
        if (ac.isPresent() && ac.get().isEquipped(item)) {
            SlotEntryReference itemRef = ac.get().getFirstEquipped(item);
            if (itemRef != null) {
                AccessoriesContainer container = itemRef.reference().slotContainer();
                boolean visible = true;
                if (container != null) visible = container.renderOptions().get(0);
                return new BooleanObjectImmutablePair<>(visible, itemRef.stack());
            }
        }
        return new BooleanObjectImmutablePair<>(false, ItemStack.EMPTY);
    }

    public static boolean accessoryEquipped(LivingEntity entity, Item item) {
        Optional<AccessoriesCapability> playerCapability = AccessoriesCapability.getOptionally(entity);
        return playerCapability.map(accessoriesCapability -> accessoriesCapability.isEquipped(item)).orElse(false);
    }

    public static boolean calendarAccessoryEquipped(LivingEntity entity) {
        if (CompatFlags.SERENE_SEASONS_LOADED) return SereneSeasonsCompat.calendarAccessoryEquipped(entity);
        else if (CompatFlags.FABRIC_SEASONS_LOADED && CompatFlags.FABRIC_SEASONS_EXTRAS_LOADED) return FabricSeasonsCompat.calendarAccessoryEquipped(entity);
        return false;
    }

    public static BooleanObjectImmutablePair<ItemStack> tryGetElytraAccessory(LivingEntity livingEntity) {
        BooleanObjectImmutablePair<ItemStack> pair = new BooleanObjectImmutablePair<>(false, ItemStack.EMPTY);
        //? if <= 1.21.1 {
        if (CompatFlags.DEEPER_DARKER_LOADED) {
            pair = DeeperDarkerCompat.getSoulElytraAccessoryStack(livingEntity);
        }
        //?}
        if (pair.right().isEmpty()) {
            pair = getAccessoryStack(livingEntity, Items.ELYTRA);
        }
        return pair;
    }

    //? if <= 1.21.1 {
    public static boolean moddedElytraCheck(ItemStack stack, LivingEntity livingEntity, boolean original) {
        if (
                !tryGetElytraAccessory(livingEntity).right().isEmpty() &&
                !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)
        ) {
            return stack.getItem() instanceof ElytraItem;
        }
        return original;
    }
    //?}

    public static boolean isTotem(ItemStack stack) {
        //? if 1.20.1 {
        /*if (CompatFlags.FRIENDS_AND_FOES_LOADED) {
            return FriendsAndFoesCompat.isTotem(stack) || stack.is(Items.TOTEM_OF_UNDYING);
        }
        return stack.is(Items.TOTEM_OF_UNDYING);
        *///?}
        //? if 1.21.1 {
        if (CompatFlags.FRIENDS_AND_FOES_LOADED && CompatFlags.ARS_ELIXIRUM_LOADED) {
            return FriendsAndFoesCompat.isTotem(stack) || ArsElixirumCompat.isTotem(stack) || stack.is(Items.TOTEM_OF_UNDYING);
        } else if (CompatFlags.ARS_ELIXIRUM_LOADED) {
            return ArsElixirumCompat.isTotem(stack) || stack.is(Items.TOTEM_OF_UNDYING);
        } else if (CompatFlags.FRIENDS_AND_FOES_LOADED) {
            return FriendsAndFoesCompat.isTotem(stack) || stack.is(Items.TOTEM_OF_UNDYING);
        }
        return stack.is(Items.TOTEM_OF_UNDYING);
        //?}
        //? if > 1.21.1
        /*return stack.has(DataComponents.DEATH_PROTECTION);*/
    }

    public static ItemStack tryGetTotemAccessory(LivingEntity livingEntity) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(livingEntity);
        if (ac.isPresent()) {
            List<SlotEntryReference> totems = ac.get().getEquipped(ModUtil::isTotem);
            if (!totems.isEmpty()) {
                return totems.get(0).stack();
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean isArrow(ItemStack stack) {
        return ARROWS.stream().anyMatch(stack::is);
    }

    public static boolean isLantern(ItemStack stack) {
        return LANTERNS.stream().anyMatch(stack::is);
    }

    public static boolean isHoldingProjectileWeapon(Player player) {
        for (ItemStack stack : player.getHandSlots()) if (stack.getItem() instanceof ProjectileWeaponItem) return true;
        return false;
    }

    public static boolean calendarUsedForSeasonInfo() {
        return Main.CONFIG.accessorySettings.calendarAccessory.get() && (CompatFlags.SERENE_SEASONS_LOADED || (CompatFlags.FABRIC_SEASONS_LOADED && CompatFlags.FABRIC_SEASONS_EXTRAS_LOADED));
    }
}
