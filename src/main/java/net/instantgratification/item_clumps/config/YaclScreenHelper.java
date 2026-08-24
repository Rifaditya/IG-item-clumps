/*
 * Copyright (C) 2026 Rifaditya (Dasik)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.instantgratification.item_clumps.config;

// Verified against: YaclScreenHelper.java (YACL 3.9.5)
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.fabricmc.loader.api.FabricLoader;

public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        ItemClumpsConfig config = ItemClumpsConfig.get();
        boolean hasStackSizeAdjuster = FabricLoader.getInstance().isModLoaded("stack-size-adjuster");

        OptionGroup.Builder groupBuilder = OptionGroup.createBuilder()
                .name(Component.translatable("config.item_clumps.group.options"));

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
                .customController(opt -> new IntegerSliderController(opt, 1, 10, 1))
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
