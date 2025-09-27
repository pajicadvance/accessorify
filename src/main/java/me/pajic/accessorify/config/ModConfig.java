package me.pajic.accessorify.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.pajic.accessorify.Main;

@Version(version = 1)
public class ModConfig extends Config {
    public ModConfig() {
        super(Main.CONFIG_RL);
    }

    @RequiresAction(action = Action.RESTART)
    public ValidatedEnum<SlotMode> slotMode = new ValidatedEnum<>(SlotMode.DEFAULT_SLOT);
    public AccessorySettings accessorySettings = new AccessorySettings();
    public InfoOverlaySettings infoOverlaySettings = new InfoOverlaySettings();
    public ValidatedBoolean hideDebugInfoInSurvival = new ValidatedBoolean(false);
    public ValidatedBoolean cancelElytraFlyingInLiquid = new ValidatedBoolean(true);

    @RequiresAction(action = Action.RESTART)
    public static class AccessorySettings extends ConfigSection {
        public ValidatedBoolean clockAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean compassAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean recoveryCompassAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean calendarAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean elytraAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean spyglassAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean lanternAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean totemOfUndyingAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean enderChestAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean shulkerBoxAccessory = new ValidatedBoolean(true);
        public ValidatedBoolean arrowAccessory = new ValidatedBoolean(true);
    }

    public static class InfoOverlaySettings extends ConfigSection {
        public OverlayFields overlayFields = new OverlayFields();
        public ValidatedBoolean showYCoordinate = new ValidatedBoolean(true);
        public ValidatedBoolean obfuscateCompassIfNotOverworld = new ValidatedBoolean(false);
        public ValidatedBoolean obfuscateClockIfNotOverworld = new ValidatedBoolean(true);
        public ValidatedBoolean useObfuscationEffect = new ValidatedBoolean(true);
    }

    public static class OverlayFields extends ConfigSection {
        public ValidatedBoolean coordinates = new ValidatedBoolean(true);
        public ValidatedBoolean direction = new ValidatedBoolean(true);
        public ValidatedBoolean biome = new ValidatedBoolean(true);
        public ValidatedBoolean dayAndTime = new ValidatedBoolean(true);
        public ValidatedBoolean weather = new ValidatedBoolean(true);
        public ValidatedBoolean moonPhase = new ValidatedBoolean(true);
        public ValidatedBoolean season = new ValidatedBoolean(true);
        public ValidatedBoolean lastDeathLocation = new ValidatedBoolean(true);
    }
}