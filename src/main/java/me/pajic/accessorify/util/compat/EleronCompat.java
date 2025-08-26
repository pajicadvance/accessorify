package me.pajic.accessorify.util.compat;

import com.sindercube.eleron.registry.EleronAttributes;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EleronCompat {
    public static void addModifiers(AccessoryAttributeBuilder builder, ItemStack stack, HolderLookup.RegistryLookup<Enchantment> registry) {
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, MultiVersionUtil.parse("eleron:cloudskipper"))).ifPresent(
                ref -> builder.addExclusive(
                        EleronAttributes.ALTITUDE_DRAG_REDUCTION/*? if 1.20.1 {*//*.value()*//*?}*/,
                        new AttributeModifier(
                                //? if >= 1.21.1
                                MultiVersionUtil.withModNamespace("cloudskipper_modifier"),
                                //? if 1.20.1
                                /*"cloudskipper_modifier",*/
                                EnchantmentHelper.getItemEnchantmentLevel(ref/*? if 1.20.1 {*//*.value()*//*?}*/, stack),
                                MultiVersionUtil.operationAdd()
                        )
                )
        );
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, MultiVersionUtil.parse("eleron:smokestack"))).ifPresent(
                ref -> builder.addExclusive(
                        EleronAttributes.MAX_SMOKESTACK_CHARGES/*? if 1.20.1 {*//*.value()*//*?}*/,
                        new AttributeModifier(
                                //? if >= 1.21.1
                                MultiVersionUtil.withModNamespace("smokestack_modifier"),
                                //? if 1.20.1
                                /*"smokestack_modifier",*/
                                EnchantmentHelper.getItemEnchantmentLevel(ref/*? if 1.20.1 {*//*.value()*//*?}*/, stack),
                                MultiVersionUtil.operationAdd()
                        )
                )
        );
    }
}
