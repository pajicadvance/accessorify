package me.pajic.accessorify.accessories.compat;

import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.AccessoryExtension;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GeneralUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FabricSeasonsCalendarAccessory implements AccessoryExtension {
	public static final ResourceLocation ITEM_ID = GeneralUtil.customId("seasonsextras", "season_calendar");

    @Override
    public String getAttributePath() {
        return "add_charm_3";
    }

    @Override
    public String getDefaultSlot() {
        return "charm";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !AccessoryUtil.isAnotherEquipped(stack, reference, stack1 ->
				stack1.is(GeneralUtil.itemFromId(ITEM_ID))
		);
    }
}
