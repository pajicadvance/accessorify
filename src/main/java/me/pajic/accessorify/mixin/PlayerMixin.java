package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.ModUtil;
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
import net.minecraft.world.item.ElytraItem;
//? if > 1.21.1
/*import net.minecraft.core.component.DataComponents;*/

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean isCreative();
    @Shadow public abstract boolean isSpectator();
    @Shadow public abstract void setReducedDebugInfo(boolean reducedDebugInfo);
    //? if < 1.21.8 {
    @Shadow public abstract @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot);
    @Shadow public abstract void stopFallFlying();
    //?}

    //? if <= 1.21.1 {
    @ModifyExpressionValue(
            method = "tryToStartFallFlying",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original) {
        if (Main.CONFIG.accessorySettings.elytraAccessory.get()) {
            ItemStack stack = ModUtil.tryGetElytraAccessory((LivingEntity) (Object) this).right();
            return stack.isEmpty() ? original : stack;
        }
        return original;
    }
    //?}

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void cancelElytraFlyingInLiquid(CallbackInfo ci) {
        if (
                isInLiquid() && (
                        //? if <= 1.21.1
                        (getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem) ||
                        //? if > 1.21.1
                        /*getItemBySlot(EquipmentSlot.CHEST).has(DataComponents.GLIDER) ||*/
                        !ModUtil.tryGetElytraAccessory((LivingEntity) (Object) this).right().isEmpty()
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
        if (Main.CONFIG.hideDebugInfoInSurvival.get()) {
            setReducedDebugInfo(!isCreative() && !isSpectator());
        }
    }

    @WrapMethod(method = "isScoping")
    private boolean modifyScopingCondition(Operation<Boolean> original) {
        if (ModUtil.shouldScope) return true;
        return original.call();
    }
}