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

    public static final ModConfigSpec SERVER_SPEC = BUILDER.build();

    public static boolean hideDebugInfoInSurvival;
    public static boolean showYCoordinate;

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
        }
    }
}
