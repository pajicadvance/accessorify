package me.pajic.accessorify.compat.deeperdarker;

import com.google.common.collect.HashMultimap;
import com.kyanite.deeperdarker.DeeperDarkerConfig;
import com.kyanite.deeperdarker.content.DDItems;
import com.kyanite.deeperdarker.network.SoulElytraClientPacket;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

public class SoulElytraAccessory implements Accessory {

    private static final ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath("accessorify", "add_cape_1");

    public static void init() {
        AccessoriesAPI.registerAccessory(DDItems.SOUL_ELYTRA.get(), new SoulElytraAccessory());
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("cape", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().addPersistentSlotModifiers(map);
        if (reference.entity() instanceof ServerPlayer player) {
            PacketDistributor.sendToPlayer(player, new SoulElytraClientPacket(true));
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var map = HashMultimap.<String, AttributeModifier>create();
        map.put("cape", new AttributeModifier(resourceLocation, 1, AttributeModifier.Operation.ADD_VALUE));
        reference.capability().removeSlotModifiers(map);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !reference.capability().isAnotherEquipped(stack, reference,
                itemStack -> itemStack.getItem() instanceof ElytraItem
        );
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        int ticks = livingEntity.getFallFlyingTicks();

        if (livingEntity.level().isClientSide() && livingEntity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown((Item)DDItems.SOUL_ELYTRA.get())) {
                float percent = player.getCooldowns().getCooldownPercent((Item)DDItems.SOUL_ELYTRA.get(), 0.0F);
                player.displayClientMessage(Component.translatable("item.deeperdarker.soul_elytra.cooldown", new Object[]{(int)Math.ceil((double)(percent * (float) DeeperDarkerConfig.soulElytraCooldown / 20.0F))}), true);
            }
        }

        if (ticks > 0 && livingEntity.isFallFlying()) {
            stack.elytraFlightTick(livingEntity, ticks);
        }
    }
}
