// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.dasik.social.api.config.DasikSupportHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        ItemClumpsConfig config = ItemClumpsConfig.get();
        boolean hasStackSizeAdjuster = FabricLoader.getInstance().isModLoaded("stack-size-adjuster");

        OptionGroup.Builder groupBuilder = OptionGroup.createBuilder()
                .name(Component.translatable("config.item_clumps.category.general"));

        Option<?> supportButton = (Option<?>) DasikSupportHelper.createYaclButton();
        if (supportButton != null) {
            groupBuilder.option(supportButton);
        }

        // Enable Clumping
        groupBuilder.option(Option.<Boolean>createBuilder()
                .name(Component.translatable("config.item_clumps.enableClumping"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.enableClumping.description")))
                .binding(
                        true,
                        () -> config.enableClumping,
                        val -> config.enableClumping = val
                )
                .controller(BooleanControllerBuilder::create)
                .build());

        // Max Clump Size (only if Stack Size Adjuster is not installed)
        if (!hasStackSizeAdjuster) {
            groupBuilder.option(Option.<Integer>createBuilder()
                    .name(Component.translatable("config.item_clumps.maxClumpSize"))
                    .description(OptionDescription.of(Component.translatable("config.item_clumps.maxClumpSize.description")))
                    .binding(
                            9999,
                            () -> config.maxClumpSize,
                            val -> config.maxClumpSize = val
                    )
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .min(64)
                            .max(Integer.MAX_VALUE)
                    )
                    .build());
        }

        // Render Labels
        groupBuilder.option(Option.<Boolean>createBuilder()
                .name(Component.translatable("config.item_clumps.renderLabels"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.renderLabels.description")))
                .binding(
                        true,
                        () -> config.renderLabels,
                        val -> config.renderLabels = val
                )
                .controller(BooleanControllerBuilder::create)
                .build());

        // Merge Radius (1-10)
        groupBuilder.option(Option.<Integer>createBuilder()
                .name(Component.translatable("config.item_clumps.mergeRadius"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.mergeRadius.description")))
                .binding(
                        1,
                        () -> config.mergeRadius,
                        val -> config.mergeRadius = val
                )
                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 10).step(1))
                .build());

        // Label Min Count
        groupBuilder.option(Option.<Integer>createBuilder()
                .name(Component.translatable("config.item_clumps.labelMinCount"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.labelMinCount.description")))
                .binding(
                        -1,
                        () -> config.labelMinCount,
                        val -> config.labelMinCount = val
                )
                .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                        .min(-1)
                        .max(Integer.MAX_VALUE)
                )
                .build());

        // Enable Visual Scaling
        groupBuilder.option(Option.<Boolean>createBuilder()
                .name(Component.translatable("config.item_clumps.enableVisualScaling"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.enableVisualScaling.description")))
                .binding(
                        true,
                        () -> config.enableVisualScaling,
                        val -> config.enableVisualScaling = val
                )
                .controller(BooleanControllerBuilder::create)
                .build());

        // Pre-Clump Explosions
        groupBuilder.option(Option.<Boolean>createBuilder()
                .name(Component.translatable("config.item_clumps.preClumpExplosions"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.preClumpExplosions.description")))
                .binding(
                        true,
                        () -> config.preClumpExplosions,
                        val -> config.preClumpExplosions = val
                )
                .controller(BooleanControllerBuilder::create)
                .build());

        // Cull Occluded Labels
        groupBuilder.option(Option.<Boolean>createBuilder()
                .name(Component.translatable("config.item_clumps.cullOccludedLabels"))
                .description(OptionDescription.of(Component.translatable("config.item_clumps.cullOccludedLabels.description")))
                .binding(
                        true,
                        () -> config.cullOccludedLabels,
                        val -> config.cullOccludedLabels = val
                )
                .controller(BooleanControllerBuilder::create)
                .build());

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.item_clumps.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.item_clumps.category.general"))
                        .group(groupBuilder.build())
                        .build())
                .save(ItemClumpsConfig::save)
                .build()
                .generateScreen(parent);
    }
}
