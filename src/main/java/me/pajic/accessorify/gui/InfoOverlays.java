package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.core.Color;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.compat.RaisedCompat;
import me.pajic.accessorify.compat.SeasonsCompat;
import me.pajic.accessorify.config.ModClientConfig;
import me.pajic.accessorify.config.ModCommonConfig;
import me.pajic.accessorify.config.ModServerConfig;
import me.pajic.accessorify.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoOverlays {

    private static final List<ObjectIntImmutablePair<Component>> renderList = new ArrayList<>();

    @SubscribeEvent
    public static void renderInfoOverlays(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft minecraft = Minecraft.getInstance();
        if (
                minecraft.player != null && minecraft.level != null &&
                !minecraft.options.hideGui && !minecraft.gui.getDebugOverlay().showDebugScreen()
        ) {
            if (
                    ModCommonConfig.compassAccessory &&
                    ModCommonConfig.clockAccessory &&
                    ModUtil.accessoryEquipped(minecraft.player, Items.COMPASS) && ModUtil.accessoryEquipped(minecraft.player, Items.CLOCK)
            ) {
                prepareCompassOverlay(minecraft);
                prepareClockOverlay(minecraft);
            }
            else if (ModCommonConfig.compassAccessory && ModUtil.accessoryEquipped(minecraft.player, Items.COMPASS)) {
                prepareCompassOverlay(minecraft);
            }
            else if (ModCommonConfig.clockAccessory && ModUtil.accessoryEquipped(minecraft.player, Items.CLOCK)) {
                prepareClockOverlay(minecraft);
            }
            renderLines(guiGraphics, minecraft);
            renderList.clear();
        }
    }

    private static void prepareCompassOverlay(Minecraft minecraft) {
        BlockPos blockPos = minecraft.player.blockPosition();
        ResourceLocation biome = minecraft.player.level().getBiome(blockPos).unwrap().map(
                key -> key != null ? key.location() : null, unknown -> null
        );

        Component coordinates;
        if (ModServerConfig.showYCoordinate) {
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

        renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffff));
        renderList.add(new ObjectIntImmutablePair<>(direction, 0xffffff));
        renderList.add(new ObjectIntImmutablePair<>(biomeName, 0xffffff));
    }

    private static void prepareClockOverlay(Minecraft minecraft) {
        BlockPos blockPos = minecraft.player.blockPosition();

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
        renderList.add(new ObjectIntImmutablePair<>(dayAndTime, 0xffffff));

        if (ModList.get().isLoaded("sereneseasons")) {
            ObjectIntImmutablePair<Component> seasonStringData = SeasonsCompat.getSeasonStringData(minecraft.level);
            if (ModClientConfig.coloredSeason) {
                renderList.add(seasonStringData);
            }
            else {
                renderList.add(new ObjectIntImmutablePair<>(seasonStringData.left(), 0xffffff));
            }
        }

        Component weather;
        int weatherColor;
        if (minecraft.level.isThundering()) {
            weather = Component.translatable("gui.accessorify.thundering");
            weatherColor = ModClientConfig.thundering;
        }
        else if (minecraft.level.isRaining()) {
            //? if <= 1.21.1
            Biome.Precipitation precipitation = minecraft.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
            //? if > 1.21.1
            /*Biome.Precipitation precipitation = minecraft.level.getBiome(blockPos).value().getPrecipitationAt(blockPos, (int) minecraft.player.getY());*/
            if (precipitation == Biome.Precipitation.RAIN) {
                weather = Component.translatable("gui.accessorify.raining");
                weatherColor = ModClientConfig.raining;
            }
            else if (precipitation == Biome.Precipitation.SNOW) {
                weather = Component.translatable("gui.accessorify.snowing");
                weatherColor = ModClientConfig.snowing;
            }
            else {
                weather = Component.translatable("gui.accessorify.cloudy");
                weatherColor = ModClientConfig.cloudy;
            }
        }
        else {
            weather = Component.translatable("gui.accessorify.clear");
            weatherColor = 0xffffff;
        }
        if (ModClientConfig.coloredWeather) {
            renderList.add(new ObjectIntImmutablePair<>(weather, weatherColor));
        }
        else {
            renderList.add(new ObjectIntImmutablePair<>(weather, 0xffffff));
        }
    }

    private static void renderLines(GuiGraphics guiGraphics, Minecraft minecraft) {
        final int[] y = {4};
        OverlayPosition position = ModClientConfig.position;
        if (position == OverlayPosition.BOTTOM_LEFT || position == OverlayPosition.BOTTOM_RIGHT) {
            Collections.reverse(renderList);
        }
        renderList.forEach(line -> {
            renderLine(guiGraphics, minecraft.font, line.left(), y[0], line.rightInt(), minecraft);
            y[0] += 12;
        });
    }

    private static void renderLine(GuiGraphics guiGraphics, Font font, Component text, int lineY, int color, Minecraft minecraft) {
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        int offsetX = ModClientConfig.offsetX;
        int offsetY = ModClientConfig.offsetY;
        int raisedOffsetX = 0;
        int raisedOffsetY = 0;
        if (ModList.get().isLoaded("raised")) {
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

    public enum OverlayPosition {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }
}
