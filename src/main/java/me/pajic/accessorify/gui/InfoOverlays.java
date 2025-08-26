package me.pajic.accessorify.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.ClientMain;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.util.MultiVersionUtil;
import me.pajic.accessorify.util.compat.CompatFlags;
import me.pajic.accessorify.util.compat.RaisedCompat;
import me.pajic.accessorify.util.compat.SereneSeasonsCompat;
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

@SuppressWarnings("ConstantConditions")
public class InfoOverlays {

    private static final List<ObjectIntImmutablePair<Component>> renderList = new ArrayList<>();
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderInfoOverlays(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        if (
                MC.player != null && MC.level != null &&
                        !MC.options.hideGui && !MultiVersionUtil.debugScreenShown()
        ) {
            boolean shouldObfuscateCompass = Main.CONFIG.infoOverlaySettings.obfuscateCompassIfNotOverworld.get() && MC.level.dimension() != Level.OVERWORLD;
            boolean shouldObfuscateClock = Main.CONFIG.infoOverlaySettings.obfuscateClockIfNotOverworld.get() && MC.level.dimension() != Level.OVERWORLD;
            if (Main.CONFIG.accessorySettings.compassAccessory.get() && ModUtil.accessoryEquipped(MC.player, Items.COMPASS)) {
                prepareCompassOverlay(shouldObfuscateCompass);
            }
            if (Main.CONFIG.accessorySettings.clockAccessory.get() && ModUtil.accessoryEquipped(MC.player, Items.CLOCK)) {
                prepareClockOverlay(shouldObfuscateClock);
            }
            if (ModUtil.calendarUsedForSeasonInfo() && ModUtil.calendarAccessoryEquipped(MC.player)) {
                prepareSeasonString(shouldObfuscateClock);
            }
            if (Main.CONFIG.accessorySettings.recoveryCompassAccessory.get() && ModUtil.accessoryEquipped(MC.player, Items.RECOVERY_COMPASS)) {
                prepareRecoveryCompassOverlay();
            }
            if (!renderList.isEmpty()) {
                renderLines(guiGraphics);
                renderList.clear();
            }
        }
    }

    private static void prepareCompassOverlay(boolean shouldObfuscate) {
        if (shouldObfuscate) {
            if (Main.CONFIG.infoOverlaySettings.useObfuscationEffect.get()) {
                Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.coordinates.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.direction.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.biome.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
            }
        } else {
            BlockPos blockPos = MC.player.blockPosition();

            if (Main.CONFIG.infoOverlaySettings.overlayFields.coordinates.get()) {
                Component coordinates;
                if (Main.CONFIG.infoOverlaySettings.showYCoordinate.get()) {
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
                renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffffff));
            }

            if (Main.CONFIG.infoOverlaySettings.overlayFields.direction.get()) {
                Component direction = Component.translatable("gui.accessorify.facing", MC.player.getDirection().getName());
                renderList.add(new ObjectIntImmutablePair<>(direction, 0xffffffff));
            }

            if (Main.CONFIG.infoOverlaySettings.overlayFields.biome.get()) {
                ResourceLocation biome = MC.player.level().getBiome(blockPos).unwrap().map(
                        key -> key != null ? key.location() : null, unknown -> null
                );
                Component biomeName = Component.translatable("biome." + biome.getNamespace() + "." + biome.getPath());
                renderList.add(new ObjectIntImmutablePair<>(biomeName, 0xffffffff));
            }
        }
    }

    private static void prepareClockOverlay(boolean shouldObfuscate) {
        if (shouldObfuscate) {
            if (Main.CONFIG.infoOverlaySettings.useObfuscationEffect.get()) {
                Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.dayAndTime.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.weather.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.moonPhase.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
            }
        } else {
            BlockPos blockPos = MC.player.blockPosition();

            if (Main.CONFIG.infoOverlaySettings.overlayFields.dayAndTime.get()) {
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
                renderList.add(new ObjectIntImmutablePair<>(dayAndTime, 0xffffffff));
            }

            if (Main.CONFIG.infoOverlaySettings.overlayFields.weather.get()) {
                Component weather;
                int weatherColor;
                if (MC.level.isThundering()) {
                    weather = Component.translatable("gui.accessorify.thundering");
                    weatherColor = ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.thundering.get().argb();
                } else if (MC.level.isRaining()) {
                    //? if <= 1.21.1
                    Biome.Precipitation precipitation = MC.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
                    //? if > 1.21.1
                    /*Biome.Precipitation precipitation = MC.level.getBiome(blockPos).value().getPrecipitationAt(blockPos, (int) MC.player.getY());*/
                    if (precipitation == Biome.Precipitation.RAIN) {
                        weather = Component.translatable("gui.accessorify.raining");
                        weatherColor = ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.raining.get().argb();
                    } else if (precipitation == Biome.Precipitation.SNOW) {
                        weather = Component.translatable("gui.accessorify.snowing");
                        weatherColor = ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.snowing.get().argb();
                    } else {
                        weather = Component.translatable("gui.accessorify.cloudy");
                        weatherColor = ClientMain.CLIENT_CONFIG.infoOverlaySettings.overlayColors.cloudy.get().argb();
                    }
                } else {
                    weather = Component.translatable("gui.accessorify.clear");
                    weatherColor = 0xffffffff;
                }
                if (ClientMain.CLIENT_CONFIG.infoOverlaySettings.coloredWeather.get()) {
                    renderList.add(new ObjectIntImmutablePair<>(weather, weatherColor));
                } else {
                    renderList.add(new ObjectIntImmutablePair<>(weather, 0xffffffff));
                }
            }

            if (Main.CONFIG.infoOverlaySettings.overlayFields.moonPhase.get()) {
                /* Decided to flip the emojis around due to how they are displayed in-game;
                full moon is a hollow circle, new moon is a filled circle
                It doesn't feel right so I shifted them to match the MC moon more -Meep*/
                MutableComponent moonPhase;
                switch (MC.level.getMoonPhase()) {
                    case 0 -> moonPhase = Component.literal("\uD83C\uDF11 ").append(
                            Component.translatable("gui.accessorify.full_moon"));
                    case 1 -> moonPhase = Component.literal("\uD83C\uDF18 ").append(
                            Component.translatable("gui.accessorify.waning_gibbous"));
                    case 2 -> moonPhase = Component.literal("\uD83C\uDF17 ").append(
                            Component.translatable("gui.accessorify.last_quarter"));
                    case 3 -> moonPhase = Component.literal("\uD83C\uDF16 ").append(
                            Component.translatable("gui.accessorify.waning_crescent"));
                    case 4 -> moonPhase = Component.literal("\uD83C\uDF15 ").append(
                            Component.translatable("gui.accessorify.new_moon"));
                    case 5 -> moonPhase = Component.literal("\uD83C\uDF14 ").append(
                            Component.translatable("gui.accessorify.waxing_crescent"));
                    case 6 -> moonPhase = Component.literal("\uD83C\uDF13 ").append(
                            Component.translatable("gui.accessorify.first_quarter"));
                    case 7 -> moonPhase = Component.literal("\uD83C\uDF12 ").append(
                            Component.translatable("gui.accessorify.waxing_gibbous"));
                    default -> moonPhase = Component.literal("\uD83D\uDCA5 ").append(
                            Component.translatable("gui.accessorify.moon_default"));
                }
                renderList.add(new ObjectIntImmutablePair<>(moonPhase, 0xffffffff));
            }
        }
        if (!ModUtil.calendarUsedForSeasonInfo()) {
            prepareSeasonString(shouldObfuscate);
        }
    }

    private static void prepareSeasonString(boolean shouldObfuscate) {
        if (shouldObfuscate && CompatFlags.SERENE_SEASONS_LOADED) {
            if (Main.CONFIG.infoOverlaySettings.useObfuscationEffect.get()) {
                Component obfuscatedText = Component.literal("" + ChatFormatting.WHITE + ChatFormatting.OBFUSCATED + "XXXXXXXX".substring(0, MC.level.random.nextInt(4) + 3));
                if (Main.CONFIG.infoOverlaySettings.overlayFields.season.get())
                    renderList.add(new ObjectIntImmutablePair<>(obfuscatedText, 0xffffffff));
            }
        }
        else {
            if (Main.CONFIG.infoOverlaySettings.overlayFields.season.get()) {
                ObjectIntImmutablePair<Component> seasonStringData = null;
                if (CompatFlags.SERENE_SEASONS_LOADED) {
                    seasonStringData = SereneSeasonsCompat.getSeasonStringData(MC.level);
                }
                if (seasonStringData != null) {
                    if (ClientMain.CLIENT_CONFIG.infoOverlaySettings.coloredSeason.get()) {
                        renderList.add(seasonStringData);
                    } else {
                        renderList.add(new ObjectIntImmutablePair<>(seasonStringData.left(), 0xffffffff));
                    }
                }
            }
        }
    }

    private static void prepareRecoveryCompassOverlay() {
        if (Main.CONFIG.infoOverlaySettings.overlayFields.lastDeathLocation.get()) {
            Optional<GlobalPos> optional = MC.player.getLastDeathLocation();
            if (optional.isPresent()) {
                if (optional.get().dimension() == MC.level.dimension()) {
                    BlockPos lastDeathLocation = optional.get().pos();
                    Component coordinates;
                    if (Main.CONFIG.infoOverlaySettings.showYCoordinate.get()) {
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
                            0xffffffff
                    ));
                    renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffffff));
                } else {
                    renderList.add(new ObjectIntImmutablePair<>(
                            Component.translatable("gui.accessorify.last_death_location_wrong_dimension"),
                            0xffffffff
                    ));
                }
            } else {
                renderList.add(new ObjectIntImmutablePair<>(
                        Component.translatable("gui.accessorify.last_death_location_unavailable"),
                        0xffffffff
                ));
            }
        }
    }

    private static void renderLines(GuiGraphics guiGraphics) {
        int y = 4;
        OverlayPosition position = ClientMain.CLIENT_CONFIG.infoOverlaySettings.position.get();
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
        int offsetX = ClientMain.CLIENT_CONFIG.infoOverlaySettings.offsetX.get();
        int offsetY = ClientMain.CLIENT_CONFIG.infoOverlaySettings.offsetY.get();
        int raisedOffsetX = 0;
        int raisedOffsetY = 0;
        if (CompatFlags.RAISED_LOADED) {
            IntIntImmutablePair offsets = RaisedCompat.getOtherComponentOffsets();
            raisedOffsetX = offsets.leftInt();
            raisedOffsetY = offsets.rightInt();
        }

        IntIntImmutablePair position;
        switch (ClientMain.CLIENT_CONFIG.infoOverlaySettings.position.get()) {
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

        MultiVersionUtil.startRender(guiGraphics);

        if (ClientMain.CLIENT_CONFIG.infoOverlaySettings.textBackground.get()) {
            guiGraphics.fill(
                    x - 2, y - 2, x + font.width(text) + 2, y + 10,
                    MultiVersionUtil.color(
                            MultiVersionUtil.as8BitChannel(ClientMain.CLIENT_CONFIG.infoOverlaySettings.textBackgroundOpacity.get()),
                            0, 0, 0
                    )
            );
        }
        guiGraphics.drawString(font, text, x, y, color, ClientMain.CLIENT_CONFIG.infoOverlaySettings.textShadow.get());

        MultiVersionUtil.stopRender(guiGraphics);
    }
}
