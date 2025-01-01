package me.pajic.accessorify.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "accessorify")
@Config(name = "accessorify", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    public boolean clockAccessory = true;
    public boolean compassAccessory = true;
    public boolean elytraAccessory = true;
    public boolean spyglassAccessory = true;
    public boolean totemOfUndyingAccessory = true;
    public boolean hideDebugInfoInSurvival = false;
    @Nest public Overlay overlay = new Overlay();

    public static class Overlay {
        public boolean showYCoordinate = false;
        @Sync(Option.SyncMode.NONE) public boolean textBackground = true;
        @Sync(Option.SyncMode.NONE) @RangeConstraint(min = 0.0F, max = 1.0F) public float textBackgroundOpacity = 0.3F;
        @Sync(Option.SyncMode.NONE) public boolean textShadow = false;
        @Sync(Option.SyncMode.NONE) public boolean coloredSeason = true;
        @Sync(Option.SyncMode.NONE) public boolean coloredWeather = true;
        @Sync(Option.SyncMode.NONE) @Nest public Colors colors = new Colors();
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
