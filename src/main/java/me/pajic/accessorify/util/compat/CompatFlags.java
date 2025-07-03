package me.pajic.accessorify.util.compat;

import net.fabricmc.loader.api.FabricLoader;

public class CompatFlags {
    public static final boolean DEEPER_DARKER_LOADED = FabricLoader.getInstance().isModLoaded("deeperdarker");
    public static final boolean FRIENDS_AND_FOES_LOADED = FabricLoader.getInstance().isModLoaded("friendsandfoes");
    public static final boolean ELERON_LOADED = FabricLoader.getInstance().isModLoaded("eleron");
    public static final boolean RAISED_LOADED = FabricLoader.getInstance().isModLoaded("raised");
    public static final boolean SERENE_SEASONS_LOADED = FabricLoader.getInstance().isModLoaded("sereneseasons");
    public static final boolean FABRIC_SEASONS_LOADED = FabricLoader.getInstance().isModLoaded("seasons");
    public static final boolean FABRIC_SEASONS_EXTRAS_LOADED = FabricLoader.getInstance().isModLoaded("seasonsextras");
    public static final boolean ARS_ELIXIRUM_LOADED = FabricLoader.getInstance().isModLoaded("elixirum");
    public static final boolean ADDITIONAL_LANTERNS_LOADED = FabricLoader.getInstance().isModLoaded("additionallanterns");
}