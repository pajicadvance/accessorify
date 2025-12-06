package me.pajic.accessorify.util.compat;

//? if fabric && < 1.21.10 {

/*import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.AccessorifyClient;
import me.pajic.accessorify.util.AccessoryUtil;
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
                    case SPRING -> AccessorifyClient.CONFIG.infoOverlaySettings.overlayColors.spring.get().argb();
                    case SUMMER -> AccessorifyClient.CONFIG.infoOverlaySettings.overlayColors.summer.get().argb();
                    case FALL -> AccessorifyClient.CONFIG.infoOverlaySettings.overlayColors.autumn.get().argb();
                    case WINTER -> AccessorifyClient.CONFIG.infoOverlaySettings.overlayColors.winter.get().argb();
                }
        );
    }

    public static boolean calendarAccessoryEquipped(LivingEntity entity) {
        return AccessoryUtil.isAccessoryEquipped(entity, FabricSeasonsExtras.SEASON_CALENDAR_ITEM);
    }
}
*///?}
