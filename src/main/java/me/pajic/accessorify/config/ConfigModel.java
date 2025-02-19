package me.pajic.accessorify.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "accessorify")
@Config(name = "accessorify", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    @RestartRequired public boolean clockAccessory = true;
    @RestartRequired public boolean compassAccessory = true;
    @RestartRequired public boolean recoveryCompassAccessory = true;
    @RestartRequired public boolean calendarAccessory = true;
    @RestartRequired public boolean elytraAccessory = true;
    @RestartRequired public boolean spyglassAccessory = true;
    @RestartRequired public boolean totemOfUndyingAccessory = true;
    @RestartRequired public boolean shulkerBoxAccessory = true;
    public boolean hideDebugInfoInSurvival = false;
    @Nest public Overlay overlay = new Overlay();
    @Nest public SpyglassZoom spyglassZoom = new SpyglassZoom();

    public static class SpyglassZoom {
        @Sync(Option.SyncMode.NONE) public boolean scrollableZoom = true;
        @Sync(Option.SyncMode.NONE) public boolean rememberZoomLevel = true;
    }

    public static class Overlay {
        public boolean showYCoordinate = true;
        public boolean obfuscateCompassIfNotOverworld = false;
        public boolean obfuscateClockIfNotOverworld = true;
        @Sync(Option.SyncMode.NONE) public OverlayPosition position = OverlayPosition.TOP_LEFT;
        @Sync(Option.SyncMode.NONE) @PredicateConstraint("positive") public int offsetX = 0;
        @Sync(Option.SyncMode.NONE) @PredicateConstraint("positive") public int offsetY = 0;
        @Sync(Option.SyncMode.NONE) public boolean textBackground = true;
        @Sync(Option.SyncMode.NONE) @RangeConstraint(min = 0.0F, max = 1.0F) public float textBackgroundOpacity = 0.3F;
        @Sync(Option.SyncMode.NONE) public boolean textShadow = false;
        @Sync(Option.SyncMode.NONE) public boolean coloredSeason = true;
        @Sync(Option.SyncMode.NONE) public boolean coloredWeather = true;
        @Sync(Option.SyncMode.NONE) @Nest public Colors colors = new Colors();

        public static boolean positive(int value) {
            return value >= 0;
        }
    }

    public static class Colors {
        @Sync(Option.SyncMode.NONE) public int raining = 0x52a0f7;
        @Sync(Option.SyncMode.NONE) public int thundering = 0x30639c;
        @Sync(Option.SyncMode.NONE) public int cloudy = 0x878787;
        @Sync(Option.SyncMode.NONE) public int snowing = 0x2fced2;
        @Sync(Option.SyncMode.NONE) public int spring = 0x42f55a;
        @Sync(Option.SyncMode.NONE) public int summer = 0xf2f542;
        @Sync(Option.SyncMode.NONE) public int autumn = 0xf57542;
        @Sync(Option.SyncMode.NONE) public int winter = 0x42f5f5;
    }

    public static boolean positive(int value) {
        return value >= 0;
    }
}
