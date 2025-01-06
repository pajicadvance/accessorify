package me.pajic.accessorify.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import me.pajic.accessorify.gui.InfoOverlays;

@Modmenu(modId = "accessorify")
@Config(name = "accessorify", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    @RestartRequired public boolean clockAccessory = true;
    @RestartRequired public boolean compassAccessory = true;
    @RestartRequired public boolean elytraAccessory = true;
    @RestartRequired public boolean spyglassAccessory = true;
    @RestartRequired public boolean totemOfUndyingAccessory = true;
    public boolean hideDebugInfoInSurvival = false;
    @Nest public Overlay overlay = new Overlay();

    public static class Overlay {
        public boolean showYCoordinate = false;
        @Sync(Option.SyncMode.NONE) public InfoOverlays.OverlayPosition position = InfoOverlays.OverlayPosition.TOP_LEFT;
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
}
