package net.instantgratification.item_clumps;

// Verified against: ModInitializer.java (Fabric API)
// Verified against: GameRules.java (26.1.2 Release)
// Verified against: DynamicGameRuleManager.java (DasikLibrary 1.6.9)

import net.fabricmc.api.ModInitializer;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemClumpsFabric implements ModInitializer {
    public static final String MOD_ID = "item_clumps";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameRuleCategory CUSTOM_CATEGORY = DynamicGameRuleManager.registerCategory(
        Identifier.parse(MOD_ID + ":" + MOD_ID)
    );

    public static GameRule<Boolean> ENABLE_CLUMPING;
    public static GameRule<Integer> MAX_CLUMP_SIZE;
    public static GameRule<Boolean> RENDER_LABELS;
    public static GameRule<Integer> MERGE_RADIUS;
    
    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Item Clumps Initialized");

        ENABLE_CLUMPING = DynamicGameRuleManager.booleanRule(MOD_ID + ":enable_clumping", CUSTOM_CATEGORY, true)
            .name("Enable Clumping")
            .description("When true, ground items will aggressively merge into mega-stacks to reduce entity lag. Default: true")
            .register();

        MAX_CLUMP_SIZE = DynamicGameRuleManager.integerRule(MOD_ID + ":max_clump_size", CUSTOM_CATEGORY, 9999)
            .name("Max Clump Size")
            .description("The hard cap on how many items can merge into a single entity. Prevents overflow issues. Default: 9999")
            .range(64, Integer.MAX_VALUE)
            .register();

        RENDER_LABELS = DynamicGameRuleManager.booleanRule(MOD_ID + ":render_labels", CUSTOM_CATEGORY, true)
            .name("Render Labels")
            .description("When true, renders a holographic count above item clumps larger than a normal stack. Default: true")
            .register();

        MERGE_RADIUS = DynamicGameRuleManager.integerRule(MOD_ID + ":merge_radius", CUSTOM_CATEGORY, 1)
            .name("Merge Radius")
            .description("The block radius items will search to merge with identical items. Vanilla is ~0.5. Default: 1")
            .range(1, 10)
            .register();
    }
}
