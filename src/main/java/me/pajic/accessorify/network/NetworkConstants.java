package me.pajic.accessorify.network;

import me.pajic.accessorify.util.MultiVersionUtil;
import net.minecraft.resources.ResourceLocation;

public class NetworkConstants {
    public static final ResourceLocation OPEN_SHULKER_BOX = MultiVersionUtil.fromNamespaceAndPath("open_shulker_box");
    public static final ResourceLocation OPEN_ENDER_CONTAINER = MultiVersionUtil.fromNamespaceAndPath("open_ender_container");
    public static final ResourceLocation C2S_SYNC_SHULKER_SLOT = MultiVersionUtil.fromNamespaceAndPath("c2s_sync_shulker_slot");
    public static final ResourceLocation S2C_SYNC_SHULKER_SLOT = MultiVersionUtil.fromNamespaceAndPath("s2c_sync_shulker_slot");
    public static final ResourceLocation C2S_SYNC_ARROW_SLOT = MultiVersionUtil.fromNamespaceAndPath("c2s_sync_arrow_slot");
    public static final ResourceLocation S2C_SYNC_ARROW_SLOT = MultiVersionUtil.fromNamespaceAndPath("s2c_sync_arrow_slot");
}