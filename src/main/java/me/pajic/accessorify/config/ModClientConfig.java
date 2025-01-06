package me.pajic.accessorify.config;

import me.pajic.accessorify.gui.InfoOverlays;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "accessorify", bus = EventBusSubscriber.Bus.MOD)
public class ModClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.ConfigValue<InfoOverlays.OverlayPosition> POSITION = BUILDER
            .translation("text.config.accessorify.option.overlay.position")
            .defineEnum("position", InfoOverlays.OverlayPosition.TOP_LEFT);
    private static final ModConfigSpec.IntValue OFFSET_X = BUILDER
            .translation("text.config.accessorify.option.overlay.offsetX")
            .defineInRange("offsetX", 0, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue OFFSET_Y = BUILDER
            .translation("text.config.accessorify.option.overlay.offsetY")
            .defineInRange("offsetY", 0, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue TEXT_BACKGROUND = BUILDER
            .translation("text.config.accessorify.option.textBackground")
            .define("textBackground", true);
    private static final ModConfigSpec.DoubleValue TEXT_BACKGROUND_OPACITY = BUILDER
            .translation("text.config.accessorify.option.textBackgroundOpacity")
            .defineInRange("textBackgroundOpacity", 0.3, 0.0, 1.0);
    private static final ModConfigSpec.BooleanValue TEXT_SHADOW = BUILDER
            .translation("text.config.accessorify.option.textShadow")
            .define("textShadow", false);
    private static final ModConfigSpec.BooleanValue COLORED_SEASON = BUILDER
            .translation("text.config.accessorify.option.coloredSeason")
            .define("coloredSeason", true);
    private static final ModConfigSpec.BooleanValue COLORED_WEATHER = BUILDER
            .translation("text.config.accessorify.option.coloredWeather")
            .define("coloredWeather", true);
    private static final ModConfigSpec.IntValue RAINING = BUILDER
            .translation("text.config.accessorify.option.raining")
            .defineInRange("raining", 0x52a0f7, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue THUNDERING = BUILDER
            .translation("text.config.accessorify.option.thundering")
            .defineInRange("thundering", 0x30639c, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue CLOUDY = BUILDER
            .translation("text.config.accessorify.option.cloudy")
            .defineInRange("cloudy", 0x878787, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SNOWING = BUILDER
            .translation("text.config.accessorify.option.snowing")
            .defineInRange("snowing", 0x2fced2, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SPRING = BUILDER
            .translation("text.config.accessorify.option.spring")
            .defineInRange("spring", 0x42f55a, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue SUMMER = BUILDER
            .translation("text.config.accessorify.option.summer")
            .defineInRange("summer", 0xf2f542, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue AUTUMN = BUILDER
            .translation("text.config.accessorify.option.autumn")
            .defineInRange("autumn", 0xf57542, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue WINTER = BUILDER
            .translation("text.config.accessorify.option.winter")
            .defineInRange("winter", 0x42f5f5, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec CLIENT_SPEC = BUILDER.build();

    public static InfoOverlays.OverlayPosition position;
    public static int offsetX;
    public static int offsetY;
    public static boolean textBackground;
    public static double textBackgroundOpacity;
    public static boolean textShadow;
    public static boolean coloredSeason;
    public static boolean coloredWeather;
    public static int raining;
    public static int thundering;
    public static int cloudy;
    public static int snowing;
    public static int spring;
    public static int summer;
    public static int autumn;
    public static int winter;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig(event);
    }

    @SubscribeEvent
    static void onChange(final ModConfigEvent.Reloading event) {
        updateConfig(event);
    }

    private static void updateConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            position = POSITION.get();
            offsetX = OFFSET_X.get();
            offsetY = OFFSET_Y.get();
            textBackground = TEXT_BACKGROUND.get();
            textBackgroundOpacity = TEXT_BACKGROUND_OPACITY.get();
            textShadow = TEXT_SHADOW.get();
            coloredSeason = COLORED_SEASON.get();
            coloredWeather = COLORED_WEATHER.get();
            raining = RAINING.get();
            thundering = THUNDERING.get();
            cloudy = CLOUDY.get();
            snowing = SNOWING.get();
            spring = SPRING.get();
            summer = SUMMER.get();
            autumn = AUTUMN.get();
            winter = WINTER.get();
        }
    }
}
