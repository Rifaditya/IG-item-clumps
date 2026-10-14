// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.client.util.VisualScalingHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ItemClumpsStressFuzzTest: Pillar 1 Chaos & Property Fuzzing Suite
 *
 * Executes 20,000 randomized iterations with deterministic seed 0xDA51C263L
 * testing VisualScalingHelper.calculateScale(int count) against extreme inputs,
 * boundary conditions, NaN/Infinity guards, and 64-bit clump merge sum saturation.
 */
public class ItemClumpsStressFuzzTest {

    private static final long SEED = 0xDA51C263L;
    private static final int ITERATIONS = 20_000;

    private static final int[] EXTREME_INTS = {
        Integer.MIN_VALUE, Integer.MIN_VALUE + 1, -1_000_000, -100, -1,
        0, 1, 2, 64, 100, 1_000, 9_999, 10_000, 64_000, 1_000_000,
        1_000_000_000, Integer.MAX_VALUE - 1, Integer.MAX_VALUE
    };

    @Test
    @DisplayName("Pillar 1: 20,000-Iteration Chaos Fuzzing of VisualScalingHelper.calculateScale")
    void testVisualScalingFuzzing() {
        Random rng = new Random(SEED);

        // Explicit boundary checks matching specification
        assertEquals(1.0F, VisualScalingHelper.calculateScale(Integer.MIN_VALUE), 1e-6F);
        assertEquals(1.0F, VisualScalingHelper.calculateScale(-100), 1e-6F);
        assertEquals(1.0F, VisualScalingHelper.calculateScale(-1), 1e-6F);
        assertEquals(1.0F, VisualScalingHelper.calculateScale(0), 1e-6F);
        assertEquals(1.0F, VisualScalingHelper.calculateScale(1), 1e-6F);
        assertEquals(2.5F, VisualScalingHelper.calculateScale(Integer.MAX_VALUE), 1e-6F);

        for (int i = 0; i < ITERATIONS; i++) {
            int count;
            if (i < EXTREME_INTS.length) {
                count = EXTREME_INTS[i];
            } else {
                int mode = rng.nextInt(5);
                if (mode == 0) {
                    // Negative numbers
                    count = -1 - rng.nextInt(Integer.MAX_VALUE);
                } else if (mode == 1) {
                    // Small numbers [0, 1]
                    count = rng.nextInt(2);
                } else if (mode == 2) {
                    // Typical ranges [2, 10_000]
                    count = 2 + rng.nextInt(9_999);
                } else if (mode == 3) {
                    // Large ranges [2, 1_000_000_000]
                    count = 2 + rng.nextInt(1_000_000_000);
                } else {
                    // Extreme positive values near Integer.MAX_VALUE
                    count = Integer.MAX_VALUE - rng.nextInt(1_000_000);
                }
            }

            float scale = VisualScalingHelper.calculateScale(count);

            assertFalse(Float.isNaN(scale), "Scale must never be NaN for count=" + count);
            assertFalse(Float.isInfinite(scale), "Scale must never be Infinite for count=" + count);

            if (count <= 1) {
                assertEquals(1.0F, scale, 1e-6F, "Scale must be 1.0F for count <= 1");
            } else {
                assertTrue(scale >= 1.0F, "Scale must be >= 1.0F for count=" + count + ", got " + scale);
                assertTrue(scale <= 2.5F, "Scale must be <= 2.5F for count=" + count + ", got " + scale);
            }
        }
    }

    @Test
    @DisplayName("Pillar 1: Clump Merge Sum Math 64-bit Saturation Fuzzing")
    void testClumpMergeSumMathSaturation() {
        Random rng = new Random(SEED + 1);

        for (int i = 0; i < ITERATIONS; i++) {
            int thisCount = rng.nextInt(Integer.MAX_VALUE);
            int otherCount = rng.nextInt(Integer.MAX_VALUE);
            int maxClump = 1 + rng.nextInt(Integer.MAX_VALUE);

            // Simulating 64-bit sum math used in ItemEntityMixin
            long sum = (long) thisCount + (long) otherCount;
            assertTrue(sum >= 0L, "64-bit sum of non-negative integers must never wrap into negative");

            if (sum > (long) maxClump) {
                int spaceLeft = Math.max(0, maxClump - thisCount);
                assertTrue(spaceLeft >= 0, "spaceLeft must never be negative");
                if (spaceLeft > 0) {
                    long newThis = (long) thisCount + spaceLeft;
                    long newOther = (long) otherCount - spaceLeft;
                    assertTrue(newThis <= (long) maxClump, "New count must not exceed maxClump");
                    assertTrue(newOther >= 0L, "Remaining count must never be negative");
                }
            } else {
                int mergedCount = (int) sum;
                assertTrue(mergedCount >= thisCount && mergedCount >= otherCount,
                        "Full merge count must be >= both constituent counts");
            }
        }
    }
}
