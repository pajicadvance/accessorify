package me.pajic.accessorify.util;

import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.impl.core.ExpandedContainer;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.compat.SereneSeasonsCompat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
//? if > 1.20.1
import net.minecraft.core.component.DataComponents;
//? if < 1.21.10 {
/*//? if fabric
import me.pajic.accessorify.util.compat.FabricSeasonsCompat;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ArmorItem;
*///?}

import java.util.List;

public class GameplayUtil {

	public static boolean isTotem(ItemStack stack) {
		//? if <= 1.21.1
		//return stack.is(GeneralUtil.TOTEMS);
		//? if > 1.21.1
		return stack.is(GeneralUtil.TOTEMS) || stack.has(DataComponents.DEATH_PROTECTION);
	}

	public static boolean isLantern(ItemStack stack) {
		return stack.is(GeneralUtil.LANTERNS);
	}

	public static boolean isElytra(ItemStack stack) {
		//? if <= 1.21.1
		//return stack.getItem() instanceof ElytraItem;
		//? if > 1.21.1
		return stack.has(DataComponents.GLIDER);
	}

	public static boolean isHoldingProjectileWeapon(Player player) {
		//? if < 1.21.10
		//for (ItemStack stack : player.getHandSlots()) if (stack.getItem() instanceof ProjectileWeaponItem) return true;
		//? if >= 1.21.10
		if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem || player.getOffhandItem().getItem() instanceof ProjectileWeaponItem) return true;
		return false;
	}

	public static List<ItemStack> getContainerItems(ExpandedContainer container) {
		return container./*? if <= 1.20.1 {*//*items*//*?} else {*/getItems()/*?}*/;
	}

	public static boolean calendarAccessoryEquipped(LivingEntity entity) {
		if (CompatFlags.SERENE_SEASONS_LOADED) return SereneSeasonsCompat.calendarAccessoryEquipped(entity);
		//? if < 1.21.10 && fabric
		//else if (CompatFlags.FABRIC_SEASONS_LOADED) return FabricSeasonsCompat.calendarAccessoryEquipped(entity);
		return false;
	}

	public static boolean calendarUsedForSeasonInfo() {
		return Accessorify.CONFIG.accessorySettings.calendarAccessory.get() && (CompatFlags.SERENE_SEASONS_LOADED || CompatFlags.FABRIC_SEASONS_LOADED);
	}

	//? if < 1.21.10 {
	/*public static boolean hasArmor(SlotReference reference) {
		if (reference.entity() instanceof Player player) {
			for (ItemStack stack : player.getArmorSlots()) {
				if (stack.getItem() instanceof ArmorItem armorItem) {
					if (armorItem.getType() == ArmorItem.Type.CHESTPLATE || armorItem.getType() == ArmorItem.Type.LEGGINGS) {
						return true;
					}
				}
			}
		}
		return false;
	}
	*///?}
}
