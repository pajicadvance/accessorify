package me.pajic.accessorify.compat;

import com.sindercube.eleron.registry.EleronAttributes;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EleronCompat {

    public static void addModifiers(AccessoryAttributeBuilder builder, ItemStack stack, HolderLookup.RegistryLookup<Enchantment> registry) {
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("eleron:cloudskipper"))).ifPresent(
                ref -> builder.addExclusive(
                        EleronAttributes.ALTITUDE_DRAG_REDUCTION,
                        new AttributeModifier(
                                ResourceLocation.parse("accessorify:eleron_cloudskipper_modifier"),
                                stack.getEnchantments().getLevel(ref),
                                AttributeModifier.Operation.ADD_VALUE
                        )
                )
        );
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.parse("eleron:smokestack"))).ifPresent(
                ref -> builder.addExclusive(
                        EleronAttributes.MAX_SMOKESTACK_CHARGES,
                        new AttributeModifier(
                                ResourceLocation.parse("accessorify:eleron_smokestack_modifier"),
                                stack.getEnchantments().getLevel(ref),
                                AttributeModifier.Operation.ADD_VALUE
                        )
                )
        );
    }
}
