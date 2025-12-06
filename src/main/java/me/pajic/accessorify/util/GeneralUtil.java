package me.pajic.accessorify.util;

import me.pajic.accessorify.Accessorify;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class GeneralUtil {
	public static final TagKey<Item> LANTERNS = TagKey.create(Registries.ITEM, Accessorify.id("lanterns"));
	public static final TagKey<Item> SHULKER_BOXES = TagKey.create(Registries.ITEM, Accessorify.id("shulker_boxes"));

	public static ResourceLocation vanillaId(String path) {
		//? if 1.20.1
		//return new ResourceLocation(path);
		//? if > 1.20.1
		return ResourceLocation.withDefaultNamespace(path);
	}

	public static ResourceLocation customId(String namespace, String path) {
		//? if 1.20.1
		//return new ResourceLocation(namespace, path);
		//? if > 1.20.1
		return ResourceLocation.fromNamespaceAndPath(namespace, path);
	}

	public static Item itemFromId(ResourceLocation id) {
		//? if > 1.21.1
		return BuiltInRegistries.ITEM.getValue(id);
		//? if <= 1.21.1
		//return BuiltInRegistries.ITEM.get(id);
	}
}
