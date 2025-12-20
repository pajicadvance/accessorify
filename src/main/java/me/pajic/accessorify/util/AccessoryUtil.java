package me.pajic.accessorify.util;

import com.google.common.collect.HashMultimap;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.utils.AttributeUtils;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanObjectImmutablePair;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.renderer.LanternAccessoryRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if <= 1.21.1 {
/*import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ElytraItem;
import io.wispforest.accessories.api.AccessoriesAPI;
*///?} else {
import io.wispforest.accessories.api.core.AccessoryRegistry;
//?}

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class AccessoryUtil {

	public static ItemStack getAccessoryStack(LivingEntity entity, Item item) {
		Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(entity);
		if (ac.isPresent() && ac.get().isEquipped(item)) {
			SlotEntryReference itemRef = ac.get().getFirstEquipped(item);
			if (itemRef != null) return itemRef.stack();
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack getAccessoryStack(LivingEntity entity, Predicate<ItemStack> predicate) {
		Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(entity);
		if (ac.isPresent()) {
			List<SlotEntryReference> matchingItems = ac.get().getEquipped(predicate);
			if (!matchingItems.isEmpty()) return matchingItems.get(0).stack();
		}
		return ItemStack.EMPTY;
	}

	public static BooleanObjectImmutablePair<ItemStack> getAccessoryStackWithRenderState(LivingEntity entity, Predicate<ItemStack> predicate) {
		Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(entity);
		if (ac.isPresent() && ac.get().isEquipped(predicate)) {
			SlotEntryReference itemRef = ac.get().getFirstEquipped(predicate);
			if (itemRef != null) {
				AccessoriesContainer container = itemRef.reference().slotContainer();
				boolean visible = true;
				if (container != null) visible = container.renderOptions().get(0);
				return new BooleanObjectImmutablePair<>(visible, itemRef.stack());
			}
		}
		return new BooleanObjectImmutablePair<>(false, ItemStack.EMPTY);
	}

	public static boolean isAccessoryEquipped(LivingEntity entity, Item item) {
		Optional<AccessoriesCapability> playerCapability = AccessoriesCapability.getOptionally(entity);
		return playerCapability.map(accessoriesCapability -> accessoriesCapability.isEquipped(item)).orElse(false);
	}

	public static boolean isAccessoryEquipped(LivingEntity entity, Predicate<ItemStack> predicate) {
		Optional<AccessoriesCapability> playerCapability = AccessoriesCapability.getOptionally(entity);
		return playerCapability.map(accessoriesCapability -> accessoriesCapability.isEquipped(predicate)).orElse(false);
	}

	@SuppressWarnings("ConstantConditions")
	public static boolean isAnotherEquipped(ItemStack stack, SlotReference slot, Item item) {
		//? if 1.20.1
		//return slot.capability().isAnotherEquipped(slot, item);
		//? if > 1.20.1
		return slot.capability().isAnotherEquipped(stack, slot, item);
	}

	@SuppressWarnings("ConstantConditions")
	public static boolean isAnotherEquipped(ItemStack stack, SlotReference slot, Predicate<ItemStack> predicate) {
		//? if 1.20.1
		//return slot.capability().isAnotherEquipped(slot, predicate);
		//? if > 1.20.1
		return slot.capability().isAnotherEquipped(stack, slot, predicate);
	}

	public static void putAddAttributeModifier(HashMultimap<String, AttributeModifier> map, String slot, String path) {
		//? if 1.20.1 {
        /*Pair<String, UUID> data = AttributeUtils.getModifierData(Accessorify.id(path));
        map.put(slot, new AttributeModifier(data.second(), data.first(), 1, operationAdd()));
        *///?}
		//? if > 1.20.1
		map.put(slot, new AttributeModifier(Accessorify.id(path), 1, operationAdd()));
	}

	public static AttributeModifier.Operation operationAdd() {
		//? if 1.20.1
		//return AttributeModifier.Operation.ADDITION;
		//? if > 1.20.1
		return AttributeModifier.Operation.ADD_VALUE;
	}

    public static void registerAccessory(Item item, Accessory accessory) {
        //? if <= 1.21.1
        //AccessoriesAPI.registerAccessory(item, accessory);
        //? if > 1.21.1
        AccessoryRegistry.register(item, accessory);
    }

    public static void registerEmptyRenderer(Item... items) {
        for (Item item : items) {
			Accessorify.debugLog("Binding {} to empty renderer", item.getDescriptionId());
            //? if <= 1.21.1
            //AccessoriesRendererRegistry.registerNoRenderer(item);
            //? if > 1.21.1
            AccessoriesRendererRegistry.bindItemToEmptyRenderer(item);
        }
    }

    public static void bindItemToLanternRenderer(Item item) {
		Accessorify.debugLog("Binding {} to lantern renderer", item.getDescriptionId());
        //? if <= 1.21.1
        //AccessoriesRendererRegistry.registerRenderer(item, LanternAccessoryRenderer::new);
        //? if > 1.21.1
        AccessoriesRendererRegistry.bindItemToRenderer(item, Accessorify.id("lantern_renderer"));
    }

	//? if <= 1.21.1 {
    /*public static boolean moddedElytraCheck(ItemStack stack, LivingEntity livingEntity, boolean original) {
        if (
                !getAccessoryStack(livingEntity, GameplayUtil::isElytra).isEmpty() &&
                !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem)
        ) {
            return stack.getItem() instanceof ElytraItem;
        }
        return original;
    }
    *///?}
}
