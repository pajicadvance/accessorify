package me.pajic.accessorify.mixin.compat.citresewn;

//? if 1.21.1 && fabric {

/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import it.unimi.dsi.fastutil.booleans.BooleanObjectImmutablePair;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GameplayUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shcm.shsupercm.fabric.citresewn.defaults.cit.types.TypeElytra;

@IfModLoaded("citresewn")
@Mixin(TypeElytra.Container.class)
public class TypeElytraContainerMixin {

    @ModifyExpressionValue(
            method = "getVisualElytraItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getItemBySlot(Lnet/minecraft/world/entity/EquipmentSlot;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack tryGetElytraAccessory(ItemStack original, @Local(argsOnly = true) LivingEntity entity) {
        if (Accessorify.CONFIG.accessorySettings.elytraAccessory.get()) {
            BooleanObjectImmutablePair<ItemStack> stack = AccessoryUtil.getAccessoryStackWithRenderState(entity, GameplayUtil::isElytra);
            if (!stack.leftBoolean()) return original;
            return stack.right().isEmpty() ? original : stack.right();
        }
        return original;
    }
}
*///?}
