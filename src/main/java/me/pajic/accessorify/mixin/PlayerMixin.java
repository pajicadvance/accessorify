package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.ClientUtil;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if <= 1.21.1
//import net.minecraft.world.item.ElytraItem;
//? if > 1.21.1
import net.minecraft.core.component.DataComponents;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean isCreative();
    @Shadow public abstract boolean isSpectator();
    @Shadow public abstract void setReducedDebugInfo(boolean reducedDebugInfo);
    //? if < 1.21.10 {
    /*@Shadow public abstract @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot);
    @Shadow public abstract void stopFallFlying();
    *///?}

    //? if <= 1.21.1 {
    /*@ModifyExpressionValue(
            method = "tryToStartFallFlying",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (Accessorify.CONFIG.accessorySettings.elytraAccessory.get()) {
            ItemStack stack = AccessoryUtil.getAccessoryStack((LivingEntity) (Object) this, GameplayUtil::isElytra);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }

	//? fabric {
    @ModifyExpressionValue(
            method = "tryToStartFallFlying",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private boolean modifyElytraCheck(boolean original, @Local ItemStack itemStack) {
        return AccessoryUtil.moddedElytraCheck(itemStack, (LivingEntity) (Object) this, original);
    }
	//?}
    *///?}

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void cancelElytraFlyingInLiquid(CallbackInfo ci) {
        if (
                Accessorify.CONFIG.cancelElytraFlyingInLiquid.get() &&
                (isInWater() || isInLava()) &&
                isFallFlying() && (
                        //? if <= 1.21.1
                        //getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem ||
                        //? if > 1.21.1
                        getItemBySlot(EquipmentSlot.CHEST).has(DataComponents.GLIDER) ||
                        //? if < 1.21.10
                        //!AccessoryUtil.getAccessoryStack((LivingEntity) (Object) this, GameplayUtil::isElytra).isEmpty()
                        //? if >= 1.21.10
                        AccessoryUtil.isAccessoryEquipped((LivingEntity) (Object) this, GameplayUtil::isElytra)
                )
        ) {
            stopFallFlying();
            setSwimming(true);
        }
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void setReducedDebugInfo(CallbackInfo ci) {
        if (Accessorify.CONFIG.hideDebugInfoInSurvival.get()) {
            setReducedDebugInfo(!isCreative() && !isSpectator());
        }
    }

    @WrapMethod(method = "isScoping")
    private boolean modifyScopingCondition(Operation<Boolean> original) {
        if (ClientUtil.shouldScope) return true;
        return original.call();
    }
}
