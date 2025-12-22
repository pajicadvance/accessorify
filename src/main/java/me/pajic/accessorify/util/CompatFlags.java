package me.pajic.accessorify.util;

import me.pajic.accessorify.Accessorify;

public class CompatFlags {
    public static final boolean AILERON_LOADED = Accessorify.xplat().isModLoaded("aileron");
    public static final boolean RAISED_LOADED = Accessorify.xplat().isModLoaded("raised");
    public static final boolean SERENE_SEASONS_LOADED = Accessorify.xplat().isModLoaded("sereneseasons");
    public static final boolean FABRIC_SEASONS_LOADED = Accessorify.xplat().isModLoaded("seasons") && Accessorify.xplat().isModLoaded("seasonsextras");
    public static final boolean IMMERSIVE_OVERLAYS_LOADED = Accessorify.xplat().isModLoaded("immersiveoverlays");
	public static final boolean SPYGLASS_ASTRONOMY_LOADED = Accessorify.xplat().isModLoaded("spyglass_astronomy");
}
