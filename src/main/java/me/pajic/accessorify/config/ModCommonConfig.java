package me.pajic.accessorify.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "accessorify", bus = EventBusSubscriber.Bus.MOD)
public class ModCommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue CLOCK_ACCESSORY = BUILDER
            .translation("text.config.accessorify.option.clockAccessory")
            .gameRestart()
            .define("clockAccessory", true);
    private static final ModConfigSpec.BooleanValue COMPASS_ACCESSORY = BUILDER
            .translation("text.config.accessorify.option.compassAccessory")
            .gameRestart()
            .define("compassAccessory", true);
    private static final ModConfigSpec.BooleanValue ELYTRA_ACCESSORY = BUILDER
            .translation("text.config.accessorify.option.elytraAccessory")
            .gameRestart()
            .define("elytraAccessory", true);
    private static final ModConfigSpec.BooleanValue SPYGLASS_ACCESSORY = BUILDER
            .translation("text.config.accessorify.option.spyglassAccessory")
            .gameRestart()
            .define("spyglassAccessory", true);
    private static final ModConfigSpec.BooleanValue TOTEM_OF_UNDYING_ACCESSORY = BUILDER
            .translation("text.config.accessorify.option.totemOfUndyingAccessory")
            .gameRestart()
            .define("totemOfUndyingAccessory", true);

    public static final ModConfigSpec COMMON_SPEC = BUILDER.build();

    public static boolean clockAccessory;
    public static boolean compassAccessory;
    public static boolean elytraAccessory;
    public static boolean spyglassAccessory;
    public static boolean totemOfUndyingAccessory;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig(event);
    }

    @SubscribeEvent
    static void onChange(final ModConfigEvent.Reloading event) {
        updateConfig(event);
    }

    private static void updateConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == COMMON_SPEC) {
            clockAccessory = CLOCK_ACCESSORY.get();
            compassAccessory = COMPASS_ACCESSORY.get();
            elytraAccessory = ELYTRA_ACCESSORY.get();
            spyglassAccessory = SPYGLASS_ACCESSORY.get();
            totemOfUndyingAccessory = TOTEM_OF_UNDYING_ACCESSORY.get();
        }
    }
}
