package me.pajic.accessorify.util;

import com.google.common.collect.HashMultimap;
import com.mojang.blaze3d.systems.RenderSystem;
//$ Accessory
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
//$ ExpandedContainerImport
import io.wispforest.accessories.impl.ExpandedSimpleContainer;
import me.pajic.accessorify.Main;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
//? if <= 1.21.1 {
import io.wispforest.accessories.api.AccessoriesAPI;
import net.minecraft.util.FastColor;
//?}
//? if > 1.21.1 {
/*//$ AccessoryRegistry
import io.wispforest.accessories.api.AccessoryRegistry;
import net.minecraft.util.ARGB;
*///?}
//? if 1.20.1 {
/*import me.fzzyhmstrs.fzzy_config.networking.FzzyPayload;
import io.wispforest.accessories.utils.AttributeUtils;
import it.unimi.dsi.fastutil.Pair;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
*///?}
//? if >= 1.21.1
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//? if >= 1.21.8 {
/*import com.mojang.blaze3d.opengl.GlStateManager;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
*///?}

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class MultiVersionUtil {

    public static ResourceLocation parse(String location) {
        //? if >= 1.21.1
        return ResourceLocation.parse(location);
        //? if 1.20.1
        /*return ResourceLocation.tryParse(location);*/
    }

    public static ResourceLocation withDefaultNamespace(String path) {
        //? if >= 1.21.1
        return ResourceLocation.withDefaultNamespace(path);
        //? if 1.20.1
        /*return new ResourceLocation(path);*/
    }

    public static ResourceLocation withModNamespace(String path) {
        //? if >= 1.21.1
        return ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, path);
        //? if 1.20.1
        /*return new ResourceLocation(Main.MOD_ID, path);*/
    }

    public static AttributeModifier.Operation operationAdd() {
        //? if 1.20.1
        /*return AttributeModifier.Operation.ADDITION;*/
        //? if >= 1.21.1
        return AttributeModifier.Operation.ADD_VALUE;
    }

    public static void registerAccessory(Item item, Accessory accessory) {
        //? if <= 1.21.1
        AccessoriesAPI.registerAccessory(item, accessory);
        //? if > 1.21.1
        /*AccessoryRegistry.register(item, accessory);*/
    }

    public static void noRenderer(Item item) {
        //? if < 1.21.8
        AccessoriesRendererRegistry.registerNoRenderer(item);
        //? if >= 1.21.8
        /*AccessoriesRendererRegistry.bindItemToEmptyRenderer(item);*/
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isAnotherEquipped(ItemStack stack, SlotReference slot, Item item) {
        //? if 1.20.1
        /*return slot.capability().isAnotherEquipped(slot, item);*/
        //? if >= 1.21.1
        return slot.capability().isAnotherEquipped(stack, slot, item);
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isAnotherEquipped(ItemStack stack, SlotReference slot, Predicate<ItemStack> predicate) {
        //? if 1.20.1
        /*return slot.capability().isAnotherEquipped(slot, predicate);*/
        //? if >= 1.21.1
        return slot.capability().isAnotherEquipped(stack, slot, predicate);
    }

    public static void putAddAttributeModifier(HashMultimap<String, AttributeModifier> map, String slot, String path) {
        //? if 1.20.1 {
        /*Pair<String, UUID> data = AttributeUtils.getModifierData(withModNamespace(path));
        map.put(slot, new AttributeModifier(data.second(), data.first(), 1, operationAdd()));
        *///?}
        //? if >= 1.21.1
        map.put(slot, new AttributeModifier(withModNamespace(path), 1, operationAdd()));
    }

    //? if < 1.21.8 {
    public static NonNullList<ItemStack> getItems(ExpandedSimpleContainer container) {
        //? if 1.20.1
        /*return container.items;*/
        //? if >= 1.21.1
        return container.getItems();
    }
    //?}
    //? if >= 1.21.8 {
    /*public static List<ItemStack> getItems(ExpandedContainer container) {
        return container.getItems();
    }
    *///?}

    public static void sendToServer(CustomPacketPayload payload) {
        //? if 1.21.1
        PacketDistributor.sendToServer(payload);
        //? if >= 1.21.8
        /*ClientPacketDistributor.sendToServer(payload);*/
    }

    //? if 1.20.1 {
    /*public static void C2S(FzzyPayload payload) {
        ConfigApiJava.network().send(payload, null);
    }
    public static void S2C(FzzyPayload payload, Player player) {
        ConfigApiJava.network().send(payload, player);
    }
    *///?}

    public static int color(int a, int r, int g, int b) {
        //? if <= 1.21.1
        return FastColor.ARGB32.color(a, r, g, b);
        //? if > 1.21.1
        /*return ARGB.color(a, r, g, b);*/
    }

    public static int as8BitChannel(float value) {
        //? if > 1.21.1
        /*return ARGB.as8BitChannel(value);*/
        //? if 1.21.1
        return FastColor.as8BitChannel(value);
        //? if 1.20.1
        /*return Mth.floor(value * 255.0F);*/
    }

    public static boolean debugScreenShown() {
        //? if >= 1.21.1
        return Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen();
        //? if 1.20.1
        /*return Minecraft.getInstance().options.renderDebug;*/
    }

    //? if < 1.21.8 {
    public static boolean hasArmor(SlotReference reference) {
        if (reference.entity() instanceof Player player) {
            for (ItemStack item : player.getArmorSlots()) {
                //? if >= 1.21.1
                if (item.is(ItemTags.CHEST_ARMOR) || item.is(ItemTags.LEG_ARMOR)) return true;
                //? if 1.20.1
                /*if (item.is(CHEST_ARMOR) || item.is(LEG_ARMOR)) return true;*/
            }
        }
        return false;
    }
    //?}
    //? if >= 1.21.8 {
    /*public static boolean hasArmor(HumanoidRenderState renderState) {
        if (renderState.entityType == EntityType.PLAYER) {
            return !renderState.chestEquipment.isEmpty() || !renderState.legsEquipment.isEmpty();
        }
        return false;
    }
    *///?}

    public static void startRender(GuiGraphics guiGraphics) {
        //? if < 1.21.8 {
        guiGraphics.flush();
        RenderSystem.enableBlend();
        //?}
        //? if >= 1.21.8 {
        /*guiGraphics.nextStratum();
        GlStateManager._enableBlend();
        *///?}
    }

    public static void stopRender(GuiGraphics guiGraphics) {
        //? if < 1.21.8 {
        guiGraphics.flush();
        RenderSystem.disableBlend();
        //?}
        //? if >= 1.21.8
        /*GlStateManager._disableBlend();*/
    }
}
