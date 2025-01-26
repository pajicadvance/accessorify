package me.pajic.accessorify.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "accessorify", bus = EventBusSubscriber.Bus.MOD)
public class ModServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue HIDE_DEBUG_INFO_IN_SURVIVAL = BUILDER
            .translation("text.config.accessorify.option.hideDebugInfoInSurvival")
            .define("hideDebugInfoInSurvival", false);
    private static final ModConfigSpec.BooleanValue SHOW_Y_COORDINATE = BUILDER
            .translation("text.config.accessorify.option.showYCoordinate")
            .define("showYCoordinate", true);
    private static final ModConfigSpec.BooleanValue OBFUSCATE_COMPASS_IF_NOT_OVERWORLD = BUILDER
            .translation("text.config.accessorify.option.overlay.obfuscateCompassIfNotOverworld")
            .define("obfuscateCompassIfNotOverworld", false);
    private static final ModConfigSpec.BooleanValue OBFUSCATE_CLOCK_IF_NOT_OVERWORLD = BUILDER
            .translation("text.config.accessorify.option.overlay.obfuscateClockIfNotOverworld")
            .define("obfuscateClockIfNotOverworld", true);

    public static final ModConfigSpec SERVER_SPEC = BUILDER.build();

    public static boolean hideDebugInfoInSurvival;
    public static boolean showYCoordinate;
    public static boolean obfuscateCompassIfNotOverworld;
    public static boolean obfuscateClockIfNotOverworld;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig(event);
    }

    @SubscribeEvent
    static void onChange(final ModConfigEvent.Reloading event) {
        updateConfig(event);
    }

    private static void updateConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SERVER_SPEC) {
            hideDebugInfoInSurvival = HIDE_DEBUG_INFO_IN_SURVIVAL.get();
            showYCoordinate = SHOW_Y_COORDINATE.get();
            obfuscateCompassIfNotOverworld = OBFUSCATE_COMPASS_IF_NOT_OVERWORLD.get();
            obfuscateClockIfNotOverworld = OBFUSCATE_CLOCK_IF_NOT_OVERWORLD.get();
        }
    }
}
