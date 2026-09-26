// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemClumpsConfigTest {

    @Test
    void testDefaultValues() {
        ItemClumpsConfig config = new ItemClumpsConfig();
        assertTrue(config.enableClumping, "enableClumping should default to true");
        assertEquals(9999, config.maxClumpSize, "maxClumpSize should default to 9999");
        assertTrue(config.renderLabels, "renderLabels should default to true");
        assertEquals(1, config.mergeRadius, "mergeRadius should default to 1");
        assertEquals(-1, config.labelMinCount, "labelMinCount should default to -1");
        assertEquals(ItemClumpsConfig.VERSION, config.configVersion, "configVersion should match VERSION");
    }
}
