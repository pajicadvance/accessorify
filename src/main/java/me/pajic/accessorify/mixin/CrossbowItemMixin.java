package me.pajic.accessorify.mixin;

//? if <= 1.20.1 {

/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.PlayerExtension;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @WrapOperation(
            method = "loadProjectile",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;split(I)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private static ItemStack useAmmoFromAccessorySlot(ItemStack instance, int amount, Operation<ItemStack> original, @Local(argsOnly = true) LivingEntity shooter) {
        if (Accessorify.CONFIG.accessorySettings.arrowAccessory.get()) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(shooter);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
                if (container != null) {
                    int slot = ((PlayerExtension) shooter).accessorify$getArrowSlot();
                    ItemStack arrows = container.getAccessories().getItem(slot);
                    if (!arrows.isEmpty()) {
                        if (!shooter.level().isClientSide) arrows.shrink(amount);
                        return instance;
                    }
                }
            }
        }
        return original.call(instance, amount);
    }
}
*///?}
