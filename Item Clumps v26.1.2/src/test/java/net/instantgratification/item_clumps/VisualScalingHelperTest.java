// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.client.util.VisualScalingHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VisualScalingHelperTest {

    @Test
    void testScaleBounds() {
        assertEquals(1.0F, VisualScalingHelper.calculateScale(-10), 1e-4F);
        assertEquals(1.0F, VisualScalingHelper.calculateScale(0), 1e-4F);
        assertEquals(1.0F, VisualScalingHelper.calculateScale(1), 1e-4F);

        // 10 items: 1.0 + 0.3 * 1.0 = 1.3
        assertEquals(1.3F, VisualScalingHelper.calculateScale(10), 1e-4F);

        // 100 items: 1.0 + 0.3 * 2.0 = 1.6
        assertEquals(1.6F, VisualScalingHelper.calculateScale(100), 1e-4F);

        // 1000 items: 1.0 + 0.3 * 3.0 = 1.9
        assertEquals(1.9F, VisualScalingHelper.calculateScale(1000), 1e-4F);

        // 100000 items: 1.0 + 0.3 * 5.0 = 2.5 (capped at maxScale 2.5)
        assertEquals(2.5F, VisualScalingHelper.calculateScale(100000), 1e-4F);

        // Integer.MAX_VALUE capped at 2.5
        assertEquals(2.5F, VisualScalingHelper.calculateScale(Integer.MAX_VALUE), 1e-4F);
    }
}
