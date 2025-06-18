package me.pajic.accessorify.gui;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import org.jetbrains.annotations.NotNull;

public enum OverlayPosition implements EnumTranslatable {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

    @Override
    @NotNull public String prefix() {
        return "accessorify.overlayPosition";
    }
}
