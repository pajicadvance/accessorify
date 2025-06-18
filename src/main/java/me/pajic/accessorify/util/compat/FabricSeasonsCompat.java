package me.pajic.accessorify.util.compat;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class FabricSeasonsCompat {
    public static ObjectIntImmutablePair<Component> getSeasonStringData(Level level) {
        Season season = FabricSeasons.getCurrentSeason(level);
        return ObjectIntImmutablePair.of(
                switch (season) {
                    case SPRING -> Component.translatable("gui.accessorify.spring");
                    case SUMMER -> Component.translatable("gui.accessorify.summer");
                    case FALL -> Component.translatable("gui.accessorify.fall");
                    case WINTER -> Component.translatable("gui.accessorify.winter");
                },
                switch (season) {
                    case SPRING -> ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.spring.get().argb();
                    case SUMMER -> ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.summer.get().argb();
                    case FALL -> ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.autumn.get().argb();
                    case WINTER -> ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.winter.get().argb();
                }
        );
    }

    public static boolean calendarAccessoryEquipped(LivingEntity entity) {
        return ModUtil.accessoryEquipped(entity, FabricSeasonsExtras.SEASON_CALENDAR_ITEM);
    }
}
