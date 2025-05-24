package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.core.Color;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.compat.FabricSeasonsCompat;
import me.pajic.accessorify.util.compat.RaisedCompat;
import me.pajic.accessorify.util.compat.SereneSeasonsCompat;
import me.pajic.accessorify.util.ModUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InfoOverlays {

    public static void initOverlay() {
        HudRenderCallback.EVENT.register(InfoOverlay.INSTANCE::render);
    }

    @SuppressWarnings("ConstantConditions")
    public static class InfoOverlay implements LayeredDraw.Layer {

        protected static final InfoOverlay INSTANCE = new InfoOverlay();
        private static final List<ObjectIntImmutablePair<Component>> renderList = new ArrayList<>();
        private static final Minecraft MC = Minecraft.getInstance();

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
            if (
                    MC.player != null && MC.level != null &&
                    !MC.options.hideGui && !MC.gui.getDebugOverlay().showDebugScreen()
            ) {
                boolean shouldObfuscateCompass = Main.CONFIG.overlay.obfuscateCompassIfNotOverworld() && MC.level.dimension() != Level.OVERWORLD;
                boolean shouldObfuscateClock = Main.CONFIG.overlay.obfuscateClockIfNotOverworld() && MC.level.dimension() != Level.OVERWORLD;
                if (Main.CONFIG.compassAccessory() && ModUtil.accessoryEquipped(MC.player, Items.COMPASS)) {
                    prepareCompassOverlay(shouldObfuscateCompass);
                }
                if (Main.CONFIG.clockAccessory() && ModUtil.accessoryEquipped(MC.player, Items.CLOCK)) {
                    prepareClockOverlay(shouldObfuscateClock);
                }
                if (ModUtil.calendarUsedForSeasonInfo() && ModUtil.calendarAccessoryEquipped(MC.player)) {
                    prepareSeasonString(shouldObfuscateClock);
                }
                if (Main.CONFIG.recoveryCompassAccessory() && ModUtil.accessoryEquipped(MC.player, Items.RECOVERY_COMPASS)) {
                    prepareRecoveryCompassOverlay();
                }
                if (!renderList.isEmpty()) {
                    renderLines(guiGraphics);
                    renderList.clear();
                }
            }
        }

        private void prepareCompassOverlay(boolean shouldObfuscate) {
            if (shouldObfuscate) {
                if (Main.CONFIG.overlay.useObfuscationEffect()) {
                    Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
                }
            } else {
                BlockPos blockPos = MC.player.blockPosition();
                ResourceLocation biome = MC.player.level().getBiome(blockPos).unwrap().map(
                        key -> key != null ? key.location() : null, unknown -> null
                );

                Component coordinates;
                if (Main.CONFIG.overlay.showYCoordinate()) {
                    coordinates = Component.translatable(
                            "gui.accessorify.coordinates_xyz",
                            blockPos.getX(), blockPos.getY(), blockPos.getZ()
                    );
                } else {
                    coordinates = Component.translatable(
                            "gui.accessorify.coordinates_xz",
                            blockPos.getX(), blockPos.getZ()
                    );
                }

                Component direction = Component.translatable("gui.accessorify.facing", MC.player.getDirection().getName());
                Component biomeName = Component.translatable("biome." + biome.getNamespace() + "." + biome.getPath());

                renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffff));
                renderList.add(new ObjectIntImmutablePair<>(direction, 0xffffff));
                renderList.add(new ObjectIntImmutablePair<>(biomeName, 0xffffff));
            }
        }

        private void prepareClockOverlay(boolean shouldObfuscate) {
            if (shouldObfuscate) {
                if (Main.CONFIG.overlay.useObfuscationEffect()) {
                    Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
                }
            } else {
                BlockPos blockPos = MC.player.blockPosition();
                MutableComponent dayAndTime = Component.translatable(
                        "gui.accessorify.day",
                        (MC.level.getDayTime() / 24000L) + 1
                );
                long timeOffset = (MC.level.getDayTime() + 6000) % 24000;
                Component time = Component.translatable(
                        "gui.accessorify.time",
                        timeOffset / 1000,
                        String.format("%02d", (int) ((double) (timeOffset / 10 % 100) / 100 * 60))
                );
                dayAndTime.append(", ");
                dayAndTime.append(time);
                renderList.add(new ObjectIntImmutablePair<>(dayAndTime, 0xffffff));

                if (Main.CONFIG.overlay.displayMoonPhases()) {
                    /* Decided to flip the emojis around due to how they are displayed in-game;
                    full moon is a hollow circle, new moon is a filled circle
                    It doesn't feel right so I shifted them to match the MC moon more -Meep*/
                    MutableComponent moonPhase;
                    switch (MC.level.getMoonPhase()) {
                        case 0 -> {
                            moonPhase = Component.literal("🌑 ").append(
                                    Component.translatable("gui.accessorify.full_moon"));
                        }
                        case 1 -> {
                            moonPhase = Component.literal("🌘 ").append(
                                    Component.translatable("gui.accessorify.waning_gibbous"));
                        }
                        case 2 -> {
                            moonPhase = Component.literal("🌗 ").append(
                                    Component.translatable("gui.accessorify.last_quarter"));
                        }
                        case 3 -> {
                            moonPhase = Component.literal("🌖 ").append(
                                    Component.translatable("gui.accessorify.waning_crescent"));
                        }
                        case 4 -> {
                            moonPhase = Component.literal("🌕 ").append(
                                    Component.translatable("gui.accessorify.new_moon"));
                        }
                        case 5 -> {
                            moonPhase = Component.literal("🌔 ").append(
                                    Component.translatable("gui.accessorify.waxing_crescent"));
                        }
                        case 6 -> {
                            moonPhase = Component.literal("🌓 ").append(
                                    Component.translatable("gui.accessorify.first_quarter"));
                        }
                        case 7 -> {
                            moonPhase = Component.literal("🌒 ").append(
                                    Component.translatable("gui.accessorify.waxing_gibbous"));
                        }
                        default -> {
                            moonPhase = Component.literal("💥").append(
                                    Component.translatable("gui.accessorify.moon_default"));
                        }
                    }
                    renderList.add(new ObjectIntImmutablePair<>(moonPhase, 0xffffff));
                }

                Component weather;
                int weatherColor;
                if (MC.level.isThundering()) {
                    weather = Component.translatable("gui.accessorify.thundering");
                    weatherColor = Main.CONFIG.overlay.colors.thundering();
                } else if (MC.level.isRaining()) {
                    //? if <= 1.21.1
                    Biome.Precipitation precipitation = MC.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
                    //? if > 1.21.1
                    /*Biome.Precipitation precipitation = MC.level.getBiome(blockPos).value().getPrecipitationAt(blockPos, (int) MC.player.getY());*/
                    if (precipitation == Biome.Precipitation.RAIN) {
                        weather = Component.translatable("gui.accessorify.raining");
                        weatherColor = Main.CONFIG.overlay.colors.raining();
                    } else if (precipitation == Biome.Precipitation.SNOW) {
                        weather = Component.translatable("gui.accessorify.snowing");
                        weatherColor = Main.CONFIG.overlay.colors.snowing();
                    } else {
                        weather = Component.translatable("gui.accessorify.cloudy");
                        weatherColor = Main.CONFIG.overlay.colors.cloudy();
                    }
                } else {
                    weather = Component.translatable("gui.accessorify.clear");
                    weatherColor = 0xffffff;
                }
                if (Main.CONFIG.overlay.coloredWeather()) {
                    renderList.add(new ObjectIntImmutablePair<>(weather, weatherColor));
                } else {
                    renderList.add(new ObjectIntImmutablePair<>(weather, 0xffffff));
                }
            }
            if (!ModUtil.calendarUsedForSeasonInfo()) {
                prepareSeasonString(shouldObfuscate);
            }
        }

        private void prepareSeasonString(boolean shouldObfuscate) {
            if (shouldObfuscate && (Main.SERENE_SEASONS_LOADED || Main.FABRIC_SEASONS_LOADED)) {
                if (Main.CONFIG.overlay.useObfuscationEffect()) {
                    Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
                }
            }
            else {
                ObjectIntImmutablePair<Component> seasonStringData = null;
                if (Main.SERENE_SEASONS_LOADED) {
                    seasonStringData = SereneSeasonsCompat.getSeasonStringData(MC.level);
                } else if (Main.FABRIC_SEASONS_LOADED) {
                    seasonStringData = FabricSeasonsCompat.getSeasonStringData(MC.level);
                }
                if (seasonStringData != null) {
                    if (Main.CONFIG.overlay.coloredSeason()) {
                        renderList.add(seasonStringData);
                    } else {
                        renderList.add(new ObjectIntImmutablePair<>(seasonStringData.left(), 0xffffff));
                    }
                }
            }
        }

        private void prepareRecoveryCompassOverlay() {
            Optional<GlobalPos> optional = MC.player.getLastDeathLocation();
            if (optional.isPresent()) {
                if (optional.get().dimension() == MC.level.dimension()) {
                    BlockPos lastDeathLocation = optional.get().pos();
                    Component coordinates;
                    if (Main.CONFIG.overlay.showYCoordinate()) {
                        coordinates = Component.translatable(
                                "gui.accessorify.coordinates_xyz",
                                lastDeathLocation.getX(), lastDeathLocation.getY(), lastDeathLocation.getZ()
                        );
                    } else {
                        coordinates = Component.translatable(
                                "gui.accessorify.coordinates_xz",
                                lastDeathLocation.getX(), lastDeathLocation.getZ()
                        );
                    }
                    renderList.add(new ObjectIntImmutablePair<>(
                            Component.translatable("gui.accessorify.last_death_location"),
                            0xffffff
                    ));
                    renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffff));
                }
                else {
                    renderList.add(new ObjectIntImmutablePair<>(
                            Component.translatable("gui.accessorify.last_death_location_wrong_dimension"),
                            0xffffff
                    ));
                }
            } else {
                renderList.add(new ObjectIntImmutablePair<>(
                        Component.translatable("gui.accessorify.last_death_location_unavailable"),
                        0xffffff
                ));
            }
        }

        private void renderLines(GuiGraphics guiGraphics) {
            int y = 4;
            OverlayPosition position = Main.CONFIG.overlay.position();
            if (position == OverlayPosition.BOTTOM_LEFT || position == OverlayPosition.BOTTOM_RIGHT) {
                Collections.reverse(renderList);
            }
            for (ObjectIntImmutablePair<Component> line : renderList) {
                renderLine(guiGraphics, MC.font, line.left(), y, line.rightInt());
                y += 12;
            }
        }

        private void renderLine(GuiGraphics guiGraphics, Font font, Component text, int lineY, int color) {
            int width = MC.getWindow().getGuiScaledWidth();
            int height = MC.getWindow().getGuiScaledHeight();
            int offsetX = Main.CONFIG.overlay.offsetX();
            int offsetY = Main.CONFIG.overlay.offsetY();
            int raisedOffsetX = 0;
            int raisedOffsetY = 0;
            if (Main.RAISED_LOADED) {
                IntIntImmutablePair offsets = RaisedCompat.getOtherComponentOffsets();
                raisedOffsetX = offsets.leftInt();
                raisedOffsetY = offsets.rightInt();
            }

            IntIntImmutablePair position;
            switch (Main.CONFIG.overlay.position()) {
                case TOP_RIGHT -> position = new IntIntImmutablePair(
                        width - 4 - MC.font.width(text) - offsetX + raisedOffsetX,
                        lineY + offsetY + raisedOffsetY
                );
                case BOTTOM_LEFT -> position = new IntIntImmutablePair(
                        4 + offsetX + raisedOffsetX,
                        height - 8 - lineY - offsetY + raisedOffsetY
                );
                case BOTTOM_RIGHT -> position = new IntIntImmutablePair(
                        width - 4 - MC.font.width(text) - offsetX + raisedOffsetX,
                        height - 8 - lineY - offsetY + raisedOffsetY
                );
                default -> position = new IntIntImmutablePair(
                        4 + offsetX + raisedOffsetX,
                        lineY + offsetY + raisedOffsetY
                );
            }
            int x = position.leftInt();
            int y = position.rightInt();

            guiGraphics.flush();
            RenderSystem.enableBlend();

            if (Main.CONFIG.overlay.textBackground()) {
                guiGraphics.fill(
                        x - 2, y - 2, x + font.width(text) + 2, y + 10,
                        Color.ofHsv(0, 0, 0, Main.CONFIG.overlay.textBackgroundOpacity()).argb()
                );
            }
            guiGraphics.drawString(font, text, x, y, color, Main.CONFIG.overlay.textShadow());

            guiGraphics.flush();
            RenderSystem.disableBlend();
        }
    }
}