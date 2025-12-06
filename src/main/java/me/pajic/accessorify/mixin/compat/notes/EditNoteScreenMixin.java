package me.pajic.accessorify.mixin.compat.notes;

import com.chaosthedude.notes.gui.EditNoteScreen;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.accessorify.Accessorify;
import me.pajic.accessorify.util.AccessoryUtil;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@IfModLoaded("notes")
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(EditNoteScreen.class)
public abstract class EditNoteScreenMixin extends Screen {

    protected EditNoteScreenMixin(Component title) {
        super(title);
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/chaosthedude/notes/gui/NotesTextField;isFocused()Z"
            )
    )
    private boolean disableButtonsIfNoCompassFound(boolean original) {
        if (
                Accessorify.CONFIG.hideDebugInfoInSurvival.get() &&
                minecraft != null && minecraft.player != null && minecraft.level != null
        ) {
            return original && AccessoryUtil.isAccessoryEquipped(minecraft.player, Items.COMPASS);
        }
        return original;
    }

    @SuppressWarnings("ConstantConditions")
    @ModifyArg(
            method = "insertCoords",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/chaosthedude/notes/gui/NotesTextField;insert(Ljava/lang/String;)V"
            )
    )
    private String dontAddYIfDisabled(String newText) {
        if (!Accessorify.CONFIG.infoOverlaySettings.showYCoordinate.get()) return newText.replace(minecraft.player.getBlockY() + ", ", "");
        return newText;
    }
}
