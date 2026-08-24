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
package net.instantgratification.item_clumps;

// Verified against: ModInitializer.java (Fabric API)
// Verified against: GameRules.java (26.1.2 Release)
// Verified against: DynamicGameRuleManager.java (DasikLibrary 1.6.9)

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.resources.Identifier;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemClumpsFabric implements ModInitializer {
    public static final String MOD_ID = "item_clumps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameRuleCategory CUSTOM_CATEGORY = DynamicGameRuleManager.registerCategory(
        Identifier.fromNamespaceAndPath(MOD_ID, MOD_ID)
    );

    public static GameRule<Boolean> ENABLE_CLUMPING;
    public static GameRule<Integer> MAX_CLUMP_SIZE;
    public static GameRule<Boolean> RENDER_LABELS;
    public static GameRule<Integer> MERGE_RADIUS;
    public static GameRule<Integer> LABEL_MIN_COUNT;
    
    @Override
    public void onInitialize() {
        net.instantgratification.item_clumps.util.ModVersionGuard.checkClass("Item Clumps", "net.minecraft.world.entity.item.ItemEntity");
        // Verify Library Version compatibility
        try {
            Class.forName("net.dasik.social.api.config.ConfigHelper");
        } catch (ClassNotFoundException e) {
            net.minecraft.CrashReport report = net.minecraft.CrashReport.forThrowable(e, "Item Clumps: DasikLibrary version mismatch! Requires version 1.7.4 or higher. Please update your mods.");
            throw new net.minecraft.ReportedException(report);
        }

        LOGGER.info("Instant Gratification: Item Clumps Initialized");
        
        // Load configuration defaults
        ItemClumpsConfig.load(FabricLoader.getInstance().getConfigDir());
        ItemClumpsConfig config = ItemClumpsConfig.get();

        ENABLE_CLUMPING = DynamicGameRuleManager.booleanRule(MOD_ID + ":enable_clumping", CUSTOM_CATEGORY, config.enableClumping)
            .name("Enable Clumping")
            .description("When true, ground items will aggressively merge into mega-stacks to reduce entity lag. Default: true")
            .register();

        if (!FabricLoader.getInstance().isModLoaded("stack-size-adjuster")) {
            MAX_CLUMP_SIZE = DynamicGameRuleManager.integerRule(MOD_ID + ":max_clump_size", CUSTOM_CATEGORY, config.maxClumpSize)
                .name("Max Clump Size")
                .description("The hard cap on how many items can merge into a single entity. Prevents overflow issues. Default: 9999")
                .range(64, Integer.MAX_VALUE)
                .register();
        }

        RENDER_LABELS = DynamicGameRuleManager.booleanRule(MOD_ID + ":render_labels", CUSTOM_CATEGORY, config.renderLabels)
            .name("Render Labels")
            .description("When true, renders a holographic count above item clumps larger than a normal stack. Default: true")
            .register();

        MERGE_RADIUS = DynamicGameRuleManager.integerRule(MOD_ID + ":merge_radius", CUSTOM_CATEGORY, config.mergeRadius)
            .name("Merge Radius")
            .description("The horizontal block radius items will search to merge with identical items. To match vanilla behavior, items will not merge if one is 1 block above or below the other. Default: 1")
            .range(1, 10)
            .register();

        LABEL_MIN_COUNT = DynamicGameRuleManager.integerRule(MOD_ID + ":label_min_count", CUSTOM_CATEGORY, config.labelMinCount)
            .name("Label Min Count")
            .description("Minimum item count before the clump label displays. Set to -1 to use the default vanilla stack limit. Default: -1")
            .range(-1, Integer.MAX_VALUE)
            .register();
    }
}
