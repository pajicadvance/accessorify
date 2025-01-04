package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.core.Color;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.compat.RaisedCompat;
import me.pajic.accessorify.compat.SeasonsCompat;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class InfoOverlays {

    public static void initOverlay() {
        HudRenderCallback.EVENT.register(InfoOverlay.INSTANCE::render);
    }

    @SuppressWarnings("ConstantConditions")
    public static class InfoOverlay implements LayeredDraw.Layer {

        protected static final InfoOverlay INSTANCE = new InfoOverlay();

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
            Minecraft minecraft = Minecraft.getInstance();
            if (
                    minecraft.player != null && minecraft.level != null &&
                    !minecraft.options.hideGui && !minecraft.gui.getDebugOverlay().showDebugScreen()
            ) {
                if (
                        Main.CONFIG.compassAccessory() &&
                        Main.CONFIG.clockAccessory() &&
                        ModUtil.accessoryEquipped(minecraft.player, Items.COMPASS) && ModUtil.accessoryEquipped(minecraft.player, Items.CLOCK)
                ) {
                    renderCompassOverlay(guiGraphics, minecraft);
                    renderClockOverlay(guiGraphics, true, minecraft);
                }
                else if (Main.CONFIG.compassAccessory() && ModUtil.accessoryEquipped(minecraft.player, Items.COMPASS)) {
                    renderCompassOverlay(guiGraphics, minecraft);
                }
                else if (Main.CONFIG.clockAccessory() && ModUtil.accessoryEquipped(minecraft.player, Items.CLOCK)) {
                    renderClockOverlay(guiGraphics, false, minecraft);
                }
            }
        }

        private void renderCompassOverlay(GuiGraphics guiGraphics, Minecraft minecraft) {
            Font font = minecraft.gui.getFont();
            BlockPos blockPos = minecraft.player.blockPosition();
            ResourceLocation biome = minecraft.player.level().getBiome(blockPos).unwrap().map(
                    key -> key != null ? key.location() : null, unknown -> null
            );

            Component coordinates;
            if (Main.CONFIG.overlay.showYCoordinate()) {
                coordinates = Component.translatable(
                        "gui.accessorify.coordinates_xyz",
                        blockPos.getX(), blockPos.getY(), blockPos.getZ()
                );
            }
            else {
                coordinates = Component.translatable(
                        "gui.accessorify.coordinates_xz",
                        blockPos.getX(), blockPos.getZ()
                );
            }

            Component direction = Component.translatable("gui.accessorify.facing", minecraft.player.getDirection().getName());
            Component biomeName = Component.translatable("biome." + biome.getNamespace() + "." + biome.getPath());

            int y = 4;
            renderLine(guiGraphics, font, coordinates, y, 0xffffff);
            y += 12;
            renderLine(guiGraphics, font, direction, y, 0xffffff);
            y += 12;
            renderLine(guiGraphics, font, biomeName, y, 0xffffff);
        }

        private void renderClockOverlay(GuiGraphics guiGraphics, boolean compassRendered, Minecraft minecraft) {
            Font font = minecraft.gui.getFont();
            BlockPos blockPos = minecraft.player.blockPosition();

            int y;
            if (compassRendered) y = 40;
            else y = 4;

            MutableComponent dayAndTime = Component.translatable(
                    "gui.accessorify.day",
                    (minecraft.level.getDayTime() / 24000L) + 1
            );
            long timeOffset = (minecraft.level.getDayTime() + 6000) % 24000;
            Component time = Component.translatable(
                    "gui.accessorify.time",
                    timeOffset / 1000,
                    String.format("%02d", (int) ((double) (timeOffset / 10 % 100) / 100 * 60))
            );
            dayAndTime.append(", ");
            dayAndTime.append(time);
            renderLine(guiGraphics, font, dayAndTime, y, 0xffffff);

            if (FabricLoader.getInstance().isModLoaded("sereneseasons")) {
                ObjectIntImmutablePair<Component> seasonStringData = SeasonsCompat.getSeasonStringData(minecraft.level);
                y += 12;
                if (Main.CONFIG.overlay.coloredSeason())
                    renderLine(guiGraphics, font, seasonStringData.left(), y, seasonStringData.rightInt());
                else
                    renderLine(guiGraphics, font, seasonStringData.left(), y, 0xffffff);
            }

            Component weather;
            int weatherColor;
            if (minecraft.level.isThundering()) {
                weather = Component.translatable("gui.accessorify.thundering");
                weatherColor = Main.CONFIG.overlay.colors.thundering();
            }
            else if (minecraft.level.isRaining()) {
                //? if <= 1.21.1
                Biome.Precipitation precipitation = minecraft.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
                //? if > 1.21.1
                /*Biome.Precipitation precipitation = minecraft.level.getBiome(blockPos).value().getPrecipitationAt(blockPos, (int) minecraft.player.getY());*/
                if (precipitation == Biome.Precipitation.RAIN) {
                    weather = Component.translatable("gui.accessorify.raining");
                    weatherColor = Main.CONFIG.overlay.colors.raining();
                }
                else if (precipitation == Biome.Precipitation.SNOW) {
                    weather = Component.translatable("gui.accessorify.snowing");
                    weatherColor = Main.CONFIG.overlay.colors.snowing();
                }
                else {
                    weather = Component.translatable("gui.accessorify.cloudy");
                    weatherColor = Main.CONFIG.overlay.colors.cloudy();
                }
            }
            else {
                weather = Component.translatable("gui.accessorify.clear");
                weatherColor = 0xffffff;
            }
            y += 12;
            if (Main.CONFIG.overlay.coloredWeather())
                renderLine(guiGraphics, font, weather, y, weatherColor);
            else
                renderLine(guiGraphics, font, weather, y, 0xffffff);
        }

        private void renderLine(GuiGraphics guiGraphics, Font font, Component text, int y, int color) {
            int xOffset = 0;
            int yOffset = 0;
            if (FabricLoader.getInstance().isModLoaded("raised")) {
                IntIntImmutablePair offsets = RaisedCompat.getOtherComponentOffsets();
                xOffset = offsets.leftInt();
                yOffset = offsets.rightInt();
            }

            guiGraphics.flush();
            RenderSystem.enableBlend();

            if (Main.CONFIG.overlay.textBackground()) {
                guiGraphics.fill(
                        2 + xOffset, y - 2 + yOffset, font.width(text) + 5 + xOffset, y + 10 + yOffset,
                        Color.ofHsv(0, 0, 0, Main.CONFIG.overlay.textBackgroundOpacity()).argb()
                );
            }
            guiGraphics.drawString(font, text, 4 + xOffset, y + yOffset, color, Main.CONFIG.overlay.textShadow());

            guiGraphics.flush();
            RenderSystem.disableBlend();
        }
    }
}