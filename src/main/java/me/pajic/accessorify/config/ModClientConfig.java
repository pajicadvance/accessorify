package me.pajic.accessorify.config;

import me.fzzyhmstrs.fzzy_config.annotations.ClientModifiable;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.gui.OverlayPosition;

@ClientModifiable
@Version(version = 1)
public class ModClientConfig extends Config {
    public ModClientConfig() {
        super(ClientMain.CLIENT_CONFIG_RL);
    }

    public WidgetSettings widgetSettings = new WidgetSettings();
    public SpyglassZoomSettings spyglassZoomSettings = new SpyglassZoomSettings();
    public InfoOverlayClientSettings infoOverlaySettings = new InfoOverlayClientSettings();

    public static class WidgetSettings extends ConfigSection {
        public ValidatedBoolean showUIHints = new ValidatedBoolean(true);
        public ValidatedBoolean shulkerQuickSelect = new ValidatedBoolean(true);
    }

    public static class SpyglassZoomSettings extends ConfigSection {
        public ValidatedBoolean scrollableZoom = new ValidatedBoolean(true);
        public ValidatedBoolean rememberZoomLevel = new ValidatedBoolean(true);
    }

    public static class InfoOverlayClientSettings extends ConfigSection {
        public ValidatedEnum<OverlayPosition> position = new ValidatedEnum<>(OverlayPosition.TOP_LEFT);
        public ValidatedInt offsetX = new ValidatedInt(0, Integer.MAX_VALUE, 0);
        public ValidatedInt offsetY = new ValidatedInt(0, Integer.MAX_VALUE, 0);
        public ValidatedBoolean textBackground = new ValidatedBoolean(true);
        public ValidatedFloat textBackgroundOpacity = new ValidatedFloat(0.3F, 1F, 0F);
        public ValidatedBoolean textShadow = new ValidatedBoolean(false);
        public ValidatedBoolean coloredSeason = new ValidatedBoolean(true);
        public ValidatedBoolean coloredWeather = new ValidatedBoolean(true);
        public OverlayColors overlayColors = new OverlayColors();
    }

    public static class OverlayColors extends ConfigSection {
        public ValidatedColor raining = new ValidatedColor(82, 160, 247);
        public ValidatedColor thundering = new ValidatedColor(48, 99, 156);
        public ValidatedColor cloudy = new ValidatedColor(135, 135, 135);
        public ValidatedColor snowing = new ValidatedColor(47, 206, 210);
        public ValidatedColor spring = new ValidatedColor(66, 245, 90);
        public ValidatedColor summer = new ValidatedColor(242, 245, 66);
        public ValidatedColor autumn = new ValidatedColor(245, 117, 66);
        public ValidatedColor winter = new ValidatedColor(66, 245, 245);
    }
}
