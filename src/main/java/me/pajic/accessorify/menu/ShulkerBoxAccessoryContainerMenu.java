package me.pajic.accessorify.menu;

import me.pajic.accessorify.util.compat.CompatFlags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
//? if >= 1.21.10
/*import net.minecraft.world.entity.ContainerUser;*/
//? if <= 1.21.1
import me.pajic.accessorify.compat.reinfshulker.ReinfShulkerCompat;

public class ShulkerBoxAccessoryContainerMenu implements Container, MenuProvider {

    private final ItemStack shulker;
    protected NonNullList<ItemStack> items;

    //? if > 1.21.1 {
    /*public ShulkerBoxAccessoryContainerMenu(ItemStack shulker) {
        this.shulker = shulker;
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
    }
    *///?}
    //? if <= 1.21.1 {
    public ShulkerBoxAccessoryContainerMenu(ItemStack shulker, int size) {
        this.shulker = shulker;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }
    //?}
    //? if < 1.21.10 {
    @Override
    public void startOpen(@NotNull Player player) {
        shulker.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(items);
        player.playSound(SoundEvents.SHULKER_BOX_OPEN);
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        shulker.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
        player.playSound(SoundEvents.SHULKER_BOX_CLOSE);
    }
    //?} else {
    /*@Override
    public void startOpen(@NotNull ContainerUser user) {
        if (user instanceof Player player) {
            shulker.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(items);
            player.playSound(SoundEvents.SHULKER_BOX_OPEN);
        }
    }
    @Override
    public void stopOpen(@NotNull ContainerUser user) {
        if (user instanceof Player player) {
            shulker.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
            player.playSound(SoundEvents.SHULKER_BOX_CLOSE);
        }
    }
    *///?}

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, @Nullable ItemStack stack) {
        this.items.set(slot, stack == null ? ItemStack.EMPTY : stack);
        if (stack != null && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public void setChanged() {}

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return shulker.getHoverName();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        //? if > 1.21.1
        /*return new ShulkerBoxMenu(i, inventory, this);*/
        //? if <= 1.21.1 {
        if (CompatFlags.REINFORCED_SHULKERS_LOADED) return ReinfShulkerCompat.createMenu(i, inventory, this, shulker.getItem());
        else return new ShulkerBoxMenu(i, inventory, this);
        //?}
    }
}