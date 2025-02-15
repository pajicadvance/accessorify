package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.core.Color;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.compat.RaisedCompat;
import me.pajic.accessorify.util.compat.SereneSeasonsCompat;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.config.ModServerConfig;
import me.pajic.accessorify.config.OverlayPosition;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InfoOverlays {

    private static final List<ObjectIntImmutablePair<Component>> renderList = new ArrayList<>();
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderInfoOverlays(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (
                MC.player != null && MC.level != null &&
                !MC.options.hideGui && !MC.gui.getDebugOverlay().showDebugScreen()
        ) {
            if (ModCommonConfig.compassAccessory && ModUtil.accessoryEquipped(MC.player, Items.COMPASS)) {
                prepareCompassOverlay();
            }
            if (ModCommonConfig.clockAccessory && ModUtil.accessoryEquipped(MC.player, Items.CLOCK)) {
                prepareClockOverlay();
            }
            if (ModUtil.calendarUsedForSeasonInfo() && ModUtil.calendarAccessoryEquipped(MC.player)) {
                prepareSeasonString();
            }
            if (ModCommonConfig.recoveryCompassAccessory && ModUtil.accessoryEquipped(MC.player, Items.RECOVERY_COMPASS)) {
                prepareRecoveryCompassOverlay();
            }
            if (!renderList.isEmpty()) {
                renderLines(guiGraphics);
                renderList.clear();
            }
        }
    }

    private static void prepareCompassOverlay() {
        if (ModServerConfig.obfuscateCompassIfNotOverworld && MC.level.dimension() != Level.OVERWORLD) {
            Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
            renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
            renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
            renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
        } else {
            BlockPos blockPos = MC.player.blockPosition();
            ResourceLocation biome = MC.player.level().getBiome(blockPos).unwrap().map(
                    key -> key != null ? key.location() : null, unknown -> null
            );

            Component coordinates;
            if (ModServerConfig.showYCoordinate) {
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

    private static void prepareClockOverlay() {
        if (ModServerConfig.obfuscateClockIfNotOverworld && MC.level.dimension() != Level.OVERWORLD) {
            Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
            renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
            renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
            if (!ModUtil.calendarUsedForSeasonInfo()) renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffff));
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

            Component weather;
            int weatherColor;
            if (MC.level.isThundering()) {
                weather = Component.translatable("gui.accessorify.thundering");
                weatherColor = ModClientConfig.thundering;
            } else if (MC.level.isRaining()) {
                //? if <= 1.21.1
                Biome.Precipitation precipitation = MC.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
                //? if > 1.21.1
                /*Biome.Precipitation precipitation = MC.level.getBiome(blockPos).value().getPrecipitationAt(blockPos, (int) MC.player.getY());*/
                if (precipitation == Biome.Precipitation.RAIN) {
                    weather = Component.translatable("gui.accessorify.raining");
                    weatherColor = ModClientConfig.raining;
                } else if (precipitation == Biome.Precipitation.SNOW) {
                    weather = Component.translatable("gui.accessorify.snowing");
                    weatherColor = ModClientConfig.snowing;
                } else {
                    weather = Component.translatable("gui.accessorify.cloudy");
                    weatherColor = ModClientConfig.cloudy;
                }
            } else {
                weather = Component.translatable("gui.accessorify.clear");
                weatherColor = 0xffffff;
            }
            if (ModClientConfig.coloredWeather) {
                renderList.add(new ObjectIntImmutablePair<>(weather, weatherColor));
            } else {
                renderList.add(new ObjectIntImmutablePair<>(weather, 0xffffff));
            }

            if (!ModUtil.calendarUsedForSeasonInfo()) {
                prepareSeasonString();
            }
        }
    }

    private static void prepareSeasonString() {
        if (Main.SERENE_SEASONS_LOADED) {
            ObjectIntImmutablePair<Component> seasonStringData = SereneSeasonsCompat.getSeasonStringData(MC.level);
            if (ModClientConfig.coloredSeason) {
                renderList.add(seasonStringData);
            } else {
                renderList.add(new ObjectIntImmutablePair<>(seasonStringData.left(), 0xffffff));
            }
        }
    }

    private static void prepareRecoveryCompassOverlay() {
        Optional<GlobalPos> optional = MC.player.getLastDeathLocation();
        Component text;
        if (optional.isPresent()) {
            BlockPos lastDeathLocation = optional.get().pos();
            text = Component.translatable("gui.accessorify.last_death_location");
            Component coordinates;
            if (ModServerConfig.showYCoordinate) {
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
            renderList.add(new ObjectIntImmutablePair<>(text, 0xffffff));
            renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffff));
        } else {
            text = Component.translatable("gui.accessorify.last_death_location_unavailable");
            renderList.add(new ObjectIntImmutablePair<>(text, 0xffffff));
        }
    }

    private static void renderLines(GuiGraphics guiGraphics) {
        int y = 4;
        OverlayPosition position = ModClientConfig.position;
        if (position == OverlayPosition.BOTTOM_LEFT || position == OverlayPosition.BOTTOM_RIGHT) {
            Collections.reverse(renderList);
        }
        for (ObjectIntImmutablePair<Component> line : renderList) {
            renderLine(guiGraphics, MC.font, line.left(), y, line.rightInt());
            y += 12;
        }
    }

    private static void renderLine(GuiGraphics guiGraphics, Font font, Component text, int lineY, int color) {
        int width = MC.getWindow().getGuiScaledWidth();
        int height = MC.getWindow().getGuiScaledHeight();
        int offsetX = ModClientConfig.offsetX;
        int offsetY = ModClientConfig.offsetY;
        int raisedOffsetX = 0;
        int raisedOffsetY = 0;
        if (Main.RAISED_LOADED) {
            IntIntImmutablePair offsets = RaisedCompat.getOtherComponentOffsets();
            raisedOffsetX = offsets.leftInt();
            raisedOffsetY = offsets.rightInt();
        }

        IntIntImmutablePair position;
        switch (ModClientConfig.position) {
            case TOP_RIGHT -> position = new IntIntImmutablePair(
                    width - 4 - font.width(text) - offsetX + raisedOffsetX,
                    lineY + offsetY + raisedOffsetY
            );
            case BOTTOM_LEFT -> position = new IntIntImmutablePair(
                    4 + offsetX + raisedOffsetX,
                    height - 8 - lineY - offsetY + raisedOffsetY
            );
            case BOTTOM_RIGHT -> position = new IntIntImmutablePair(
                    width - 4 - font.width(text) - offsetX + raisedOffsetX,
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

        if (ModClientConfig.textBackground) {
            guiGraphics.fill(
                    x - 2, y - 2, x + font.width(text) + 2, y + 10,
                    Color.ofHsv(0, 0, 0, (float) ModClientConfig.textBackgroundOpacity).argb()
            );
        }
        guiGraphics.drawString(font, text, x, y, color, ModClientConfig.textShadow);

        guiGraphics.flush();
        RenderSystem.disableBlend();
    }
}
