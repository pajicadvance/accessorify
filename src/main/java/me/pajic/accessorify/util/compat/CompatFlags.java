package me.pajic.accessorify.util.compat;

import net.neoforged.fml.ModList;

public class CompatFlags {
    public static final boolean DEEPER_DARKER_LOADED = ModList.get().isLoaded("deeperdarker");
    public static final boolean RAISED_LOADED = ModList.get().isLoaded("raised");
    public static final boolean AILERON_LOADED = ModList.get().isLoaded("aileron");
    public static final boolean SERENE_SEASONS_LOADED = ModList.get().isLoaded("sereneseasons");
    public static final boolean FRIENDS_AND_FOES_LOADED = ModList.get().isLoaded("friendsandfoes");
    public static final boolean ARS_ELIXIRUM_LOADED = ModList.get().isLoaded("elixirum");
    public static final boolean ADDITIONAL_LANTERNS_LOADED = ModList.get().isLoaded("additionallanterns");
    public static final boolean IMMERSIVE_OVERLAYS_LOADED = ModList.get().isLoaded("immersiveoverlays");
}
