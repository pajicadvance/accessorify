package me.pajic.accessorify.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @WrapMethod(method = "findTotem")
    private static ItemStack findTotemAccessory(Player player, Operation<ItemStack> original) {
        if (Accessorify.CONFIG.accessorySettings.totemOfUndyingAccessory.get() && AccessoryUtil.isAccessoryEquipped(player, Items.TOTEM_OF_UNDYING)) {
            return new ItemStack(Items.TOTEM_OF_UNDYING);
        }
        return original.call(player);
    }
}
