package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.access.SelectedAccessorySlotAccess;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {

    @ModifyReturnValue(
            method = "getHeldProjectile",
            at = @At(value = "RETURN")
    )
    private static ItemStack getAmmoFromAccessorySlot(ItemStack original, @Local(argsOnly = true) LivingEntity shooter) {
        if (Main.CONFIG.accessorySettings.arrowAccessory.get() && original.isEmpty()) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(shooter);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
                if (container != null) {
                    int slot = ((SelectedAccessorySlotAccess) shooter).accessorify$getArrowSlot();
                    ItemStack arrows = container.getAccessories().getItem(slot);
                    if (!arrows.isEmpty()) {
                        return arrows;
                    }
                }
            }
        }
        return original;
    }

    @WrapMethod(method = "useAmmo")
    private static ItemStack useAmmoFromAccessorySlot(
            ItemStack weapon,
            ItemStack ammo,
            LivingEntity shooter,
            boolean intangable,
            Operation<ItemStack> original,
            @Share("accessorySlotUsed") LocalBooleanRef accessorySlotUsed,
            @Share("arrowStack") LocalRef<ItemStack> arrowStack
    ) {
        if (Main.CONFIG.accessorySettings.arrowAccessory.get()) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(shooter);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
                if (container != null) {
                    int slot = ((SelectedAccessorySlotAccess) shooter).accessorify$getArrowSlot();
                    ItemStack arrows = container.getAccessories().getItem(slot);
                    if (!arrows.isEmpty()) {
                        accessorySlotUsed.set(true);
                        arrowStack.set(arrows);
                        return original.call(weapon, container.getAccessories().getItem(slot), shooter, intangable);
                    }
                }
            }
        }
        accessorySlotUsed.set(false);
        return original.call(weapon, ammo, shooter, intangable);
    }

    @WrapWithCondition(
            method = "useAmmo",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(Lnet/minecraft/world/item/ItemStack;)V"
            )
    )
    private static boolean emptyAccessorySlot(
            Inventory instance,
            ItemStack stack,
            @Share("accessorySlotUsed") LocalBooleanRef accessorySlotUsed,
            @Share("arrowStack") LocalRef<ItemStack> arrowStack
    ) {
        return !accessorySlotUsed.get();
    }
}
