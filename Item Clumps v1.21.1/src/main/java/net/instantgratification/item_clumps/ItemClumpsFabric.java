// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

// Verified against: ModInitializer.java (Fabric API)
// Verified against: GameRules.java (1.21.1)
// Verified against: DynamicGameRuleManager.java (DasikLibrary 1.1.0)

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemClumpsFabric implements ModInitializer {
    public static final String MOD_ID = "item_clumps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static CustomGameRuleCategory CUSTOM_CATEGORY;

    public static GameRules.Key<GameRules.BooleanValue> ENABLE_CLUMPING;
    public static GameRules.Key<GameRules.IntegerValue> MAX_CLUMP_SIZE;
    public static GameRules.Key<GameRules.BooleanValue> RENDER_LABELS;
    public static GameRules.Key<GameRules.IntegerValue> MERGE_RADIUS;
    public static GameRules.Key<GameRules.IntegerValue> LABEL_MIN_COUNT;
    
    @Override
    public void onInitialize() {
        net.instantgratification.item_clumps.util.ModVersionGuard.checkClass("Item Clumps", "net.minecraft.world.entity.item.ItemEntity");
        // Verify Library Version compatibility
        try {
            Class.forName("net.dasik.social.api.config.ConfigHelper");
        } catch (ClassNotFoundException e) {
            net.minecraft.CrashReport report = net.minecraft.CrashReport.forThrowable(e, "Item Clumps: DasikLibrary version mismatch! Requires version 1.0.0 or higher. Please update your mods.");
            throw new net.minecraft.ReportedException(report);
        }

        LOGGER.info("Instant Gratification: Item Clumps Initialized (1.21.1)");
        
        // Load configuration defaults
        ItemClumpsConfig.load(FabricLoader.getInstance().getConfigDir());
        ItemClumpsConfig config = ItemClumpsConfig.get();

        CUSTOM_CATEGORY = DynamicGameRuleManager.registerCategory(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, MOD_ID),
            Component.translatable("gamerule.category.item_clumps").withStyle(ChatFormatting.BOLD, ChatFormatting.YELLOW)
        );

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
            .range(1, Integer.MAX_VALUE)
            .register();

        LABEL_MIN_COUNT = DynamicGameRuleManager.integerRule(MOD_ID + ":label_min_count", CUSTOM_CATEGORY, config.labelMinCount)
            .name("Label Min Count")
            .description("Minimum item count before the clump label displays. Set to -1 to use the default vanilla stack limit. Default: -1")
            .range(-1, Integer.MAX_VALUE)
            .register();
    }
}
