package me.pajic.accessorify.util.compat;

//? if 1.21.1 {

/*import com.lodestar.aileron.AileronAttributes;
import io.wispforest.accessories.api.attributes.AccessoryAttributeBuilder;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import me.pajic.accessorify.util.GeneralUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class AileronCompat {
    public static void addModifiers(AccessoryAttributeBuilder builder, ItemStack stack, HolderLookup.RegistryLookup<Enchantment> registry) {
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, GeneralUtil.customId("aileron", "cloudskipper"))).ifPresent(
                ref -> builder.addExclusive(
                        AileronAttributes.CLOUDSKIPPER_DRAG,
                        new AttributeModifier(
                                Accessorify.id("cloudskipper_modifier"),
                                EnchantmentHelper.getItemEnchantmentLevel(ref, stack),
                                AccessoryUtil.operationAdd()
                        )
                )
        );
        registry.get(ResourceKey.create(Registries.ENCHANTMENT, GeneralUtil.customId("aileron", "smokestack"))).ifPresent(
                ref -> builder.addExclusive(
                        AileronAttributes.SMOKESTACK_CAPACITY,
                        new AttributeModifier(
								Accessorify.id("smokestack_modifier"),
                                EnchantmentHelper.getItemEnchantmentLevel(ref, stack),
								AccessoryUtil.operationAdd()
                        )
                )
        );
    }
}
*///?}
