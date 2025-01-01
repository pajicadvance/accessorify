package me.pajic.accessorify.compat;

import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.config.ModClientConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class SeasonsCompat {
    public static ObjectIntImmutablePair<Component> getSeasonStringData(Level level) {
        Season.SubSeason subSeason = SeasonHelper.getSeasonState(level).getSubSeason();

        Component seasonName = switch (subSeason) {
            case EARLY_SPRING -> Component.translatable("gui.accessorify.early_spring");
            case MID_SPRING -> Component.translatable("gui.accessorify.mid_spring");
            case LATE_SPRING -> Component.translatable("gui.accessorify.late_spring");
            case EARLY_SUMMER -> Component.translatable("gui.accessorify.early_summer");
            case MID_SUMMER -> Component.translatable("gui.accessorify.mid_summer");
            case LATE_SUMMER -> Component.translatable("gui.accessorify.late_summer");
            case EARLY_AUTUMN -> Component.translatable("gui.accessorify.early_autumn");
            case MID_AUTUMN -> Component.translatable("gui.accessorify.mid_autumn");
            case LATE_AUTUMN -> Component.translatable("gui.accessorify.late_autumn");
            case EARLY_WINTER -> Component.translatable("gui.accessorify.early_winter");
            case MID_WINTER -> Component.translatable("gui.accessorify.mid_winter");
            case LATE_WINTER -> Component.translatable("gui.accessorify.late_winter");
        };

        int seasonColor = switch (subSeason.getSeason()) {
            case Season.SPRING -> ModClientConfig.spring;
            case Season.SUMMER -> ModClientConfig.summer;
            case Season.AUTUMN -> ModClientConfig.autumn;
            case Season.WINTER -> ModClientConfig.winter;
        };

        return ObjectIntImmutablePair.of(seasonName, seasonColor);
    }
}
