package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.config.ModServerConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean isCreative();
    @Shadow public abstract boolean isSpectator();
    @Shadow public abstract void setReducedDebugInfo(boolean reducedDebugInfo);
    @Shadow public abstract @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot);

    @Shadow public abstract void stopFallFlying();

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void setReducedDebugInfo(CallbackInfo ci) {
        if (ModServerConfig.hideDebugInfoInSurvival) {
            setReducedDebugInfo(!isCreative() && !isSpectator());
        }
    }

    @WrapMethod(method = "isScoping")
    private boolean modifyScopingCondition(Operation<Boolean> original) {
        if (ModUtil.shouldScope) return true;
        return original.call();
    }

    //? if <= 1.21.1 {
    @ModifyExpressionValue(
            method = "tryToStartFallFlying",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (ModCommonConfig.elytraAccessory) {
            ItemStack stack = ModUtil.getAccessoryStack((LivingEntity) (Object) this, Items.ELYTRA);
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
    //?}

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void cancelElytraFlyingInWater(CallbackInfo ci) {
        if (
                isInWater() && (
                        getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA) ||
                        ModUtil.accessoryEquipped((Player) (Object) this, Items.ELYTRA)
                )
        ) {
            stopFallFlying();
            setSwimming(true);
        }
    }
}