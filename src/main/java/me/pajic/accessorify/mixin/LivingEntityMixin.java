package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.ClientUtil;
import me.pajic.accessorify.util.FakeHandHolder;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow protected abstract void updateUsingItem(ItemStack usingItem);

    @WrapMethod(method = "updatingUsingItem")
    private void useSpyglassAccessory(Operation<Void> original) {
        if (ClientUtil.shouldScope) {
            updateUsingItem(new ItemStack(Items.SPYGLASS));
        }
        else original.call();
    }

    //? if <= 1.21.1 {
    /*@ModifyExpressionValue(
            method = "updateFallFlying",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (Accessorify.CONFIG.accessorySettings.elytraAccessory.get()) {
            ItemStack stack = AccessoryUtil.getAccessoryStack((LivingEntity) (Object) this, GameplayUtil::isElytra);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
    *///?}

	@WrapMethod(method = "getItemInHand")
	private ItemStack checkFakeHandForTotem(InteractionHand hand, Operation<ItemStack> original) {
		if (Accessorify.CONFIG.accessorySettings.totemOfUndyingAccessory.get() && hand == FakeHandHolder.FAKE_HAND) {
			return AccessoryUtil.getAccessoryStack((LivingEntity) (Object) this, GameplayUtil::isTotem);
		}
		return original.call(hand);
	}

	@WrapMethod(method = "setItemInHand")
	private void skipFakeHand(InteractionHand hand, ItemStack stack, Operation<Void> original) {
		if (Accessorify.CONFIG.accessorySettings.totemOfUndyingAccessory.get()) {
			if (hand != FakeHandHolder.FAKE_HAND) original.call(hand, stack);
		}
		else original.call(hand, stack);
	}
}
