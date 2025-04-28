package me.pajic.accessorify.mixin;

import me.pajic.accessorify.access.SelectedAccessorySlotAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerSelectedAccessorySlotDataMixin implements SelectedAccessorySlotAccess {
    @Unique private int shulkerSlot;
    @Unique private int arrowSlot;

    @Override
    public int accessorify$getShulkerSlot() {
        return shulkerSlot;
    }

    @Override
    public void accessorify$setShulkerSlot(int value) {
        shulkerSlot = value;
    }

    @Override
    public int accessorify$getArrowSlot() {
        return arrowSlot;
    }

    @Override
    public void accessorify$setArrowSlot(int value) {
        arrowSlot = value;
    }

    @Inject(
            method = "addAdditionalSaveData",
            at = @At("TAIL")
    )
    private void addArrowSlot(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("ShulkerSlot", shulkerSlot);
        compound.putInt("ArrowSlot", arrowSlot);
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("TAIL")
    )
    private void readArrowSlot(CompoundTag compound, CallbackInfo ci) {
        shulkerSlot = compound.getInt("ShulkerSlot");
        arrowSlot = compound.getInt("ArrowSlot");
    }
}
