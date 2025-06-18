package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    public AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(
            method = "tryPickup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    private boolean addArrowToAccessorySlots(
            Inventory instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) Player player
    ) {
        if (Main.CONFIG.accessorySettings.arrowAccessory.get()) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(new SlotTypeReference("arrow"));
                if (container != null) {
                    ExpandedSimpleContainer arrows = container.getAccessories();
                    if (!arrows.getItems().stream().allMatch(ItemStack::isEmpty) && arrows.canAddItem(itemStack)) {
                        AbstractArrow itemEntity = (AbstractArrow) (Object) this;
                        ItemStack updated = arrows.addItem(itemStack);
                        if (updated.isEmpty()) {
                            player.take(itemEntity, 1);
                            discard();
                            return false;
                        }
                        else return original.call(instance, updated);
                    }
                }
            }
        }
        return original.call(instance, itemStack);
    }
}
