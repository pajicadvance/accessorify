package me.pajic.accessorify.util.compat;

import com.lodestar.aileron.AileronAttributes;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class AileronCompat {
    public static void addModifiers(AccessoryAttributeBuilder builder, ItemStack stack, HolderLookup.RegistryLookup<Enchantment> registry) {
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, MultiVersionUtil.parse("aileron:cloudskipper"))).ifPresent(
                ref -> builder.addExclusive(
                        AileronAttributes.CLOUDSKIPPER_DRAG,
                        new AttributeModifier(
                                MultiVersionUtil.withModNamespace("cloudskipper_modifier"),
                                stack.getEnchantmentLevel(ref),
                                MultiVersionUtil.operationAdd()
                        )
                )
        );
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, MultiVersionUtil.parse("aileron:smokestack"))).ifPresent(
                ref -> builder.addExclusive(
                        AileronAttributes.SMOKESTACK_CAPACITY,
                        new AttributeModifier(
                                MultiVersionUtil.withModNamespace("smokestack_modifier"),
                                stack.getEnchantmentLevel(ref),
                                MultiVersionUtil.operationAdd()
                        )
                )
        );
    }
}