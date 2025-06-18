package me.pajic.accessorify.compat.deeperdarker;

import com.kyanite.deeperdarker.DeeperDarkerConfig;
import com.kyanite.deeperdarker.content.DDItems;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;

public class SoulElytraAccessory implements SlotCopyingAccessory {

    public static void init() {
        MultiVersionUtil.registerAccessory(DDItems.SOUL_ELYTRA.get(), new SoulElytraAccessory());
    }

    @Override
    public String getPath() {
        return "add_cape_1";
    }

    @Override
    public String getSlot() {
        return "cape";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference,
                itemStack -> itemStack.getItem() instanceof ElytraItem
        );
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        int ticks = livingEntity.getFallFlyingTicks();

        if (livingEntity.level().isClientSide() && livingEntity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(DDItems.SOUL_ELYTRA.get())) {
                float percent = player.getCooldowns().getCooldownPercent(DDItems.SOUL_ELYTRA.get(), 0.0F);
                player.displayClientMessage(Component.translatable("item.deeperdarker.soul_elytra.cooldown", (int) Math.ceil(percent * (float) DeeperDarkerConfig.soulElytraCooldown / 20.0F)), true);
            }
        }

        if (ticks > 0 && livingEntity.isFallFlying()) {
            stack.elytraFlightTick(livingEntity, ticks);
        }
    }
}
