package me.pajic.accessorify.config;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import org.jetbrains.annotations.NotNull;

public enum SlotMode implements EnumTranslatable {
    DEFAULT_SLOT, DEFAULT_SLOT_NO_COPY, UNIQUE_SLOT;

    @Override
    @NotNull public String prefix() {
        return "accessorify.slotMode";
    }
}
