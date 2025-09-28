package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import io.wispforest.accessories.data.SlotTypeLoader;
import me.pajic.accessorify.Main;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
//$ expanded_simple_container
import io.wispforest.accessories.impl.ExpandedSimpleContainer;

import java.util.Optional;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract ItemStack getItem();
    @Shadow public abstract void setItem(ItemStack stack);

    @WrapOperation(
            method = "playerTouch",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"
            )
    )
    private boolean addArrowsToAccessorySlots(
            Inventory instance, ItemStack itemStack, Operation<Boolean> original, @Local(argsOnly = true) Player player
    ) {
        if (itemStack.is(ItemTags.ARROWS) && Main.CONFIG.accessorySettings.arrowAccessory.get()) {
            Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
            if (ac.isPresent()) {
                AccessoriesContainer container = ac.get().getContainer(SlotTypeLoader.getSlotType(player, "arrow"));
                if (container != null) {
                    /*? < 1.21.8 {*/ExpandedSimpleContainer/*?} else {*//*ExpandedContainer*//*?}*/ arrows = container.getAccessories();
                    if (!arrows.getItems().stream().allMatch(ItemStack::isEmpty) && arrows.canAddItem(itemStack)) {
                        ItemEntity itemEntity = (ItemEntity) (Object) this;
                        int i = itemStack.getCount();
                        ItemStack updated = arrows.addItem(itemStack);
                        setItem(updated);
                        player.take(itemEntity, i);
                        player.awardStat(Stats.ITEM_PICKED_UP.get(itemStack.getItem()), i);
                        player.onItemPickup(itemEntity);
                        if (updated.isEmpty()) {
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
