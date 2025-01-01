package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.wispforest.accessories.api.AccessoriesCapability;
import me.pajic.accessorify.config.ModCommonConfig;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @WrapMethod(method = "findTotem")
    private static ItemStack findTotemAccessory(Player player, Operation<ItemStack> original) {
        if (ModCommonConfig.totemOfUndyingAccessory && AccessoriesCapability.get(player).isEquipped(Items.TOTEM_OF_UNDYING)) {
            return new ItemStack(Items.TOTEM_OF_UNDYING);
        }
        return original.call(player);
    }
}
