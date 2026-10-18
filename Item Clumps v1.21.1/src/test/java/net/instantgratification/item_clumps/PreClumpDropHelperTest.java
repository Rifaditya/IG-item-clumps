// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.explosion.PreClumpDropHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class PreClumpDropHelperTest {

    @AfterEach
    void tearDown() {
        PreClumpDropHelper.clear();
    }

    @Test
    @DisplayName("PreClumpDropHelper: Lifecycle and Re-entrancy Active Status")
    void testLifecycle() {
        assertFalse(PreClumpDropHelper.isPreClumpingActive(), "Initially inactive");

        PreClumpDropHelper.begin(null);
        assertTrue(PreClumpDropHelper.isPreClumpingActive(), "Active after begin");

        // Re-entrant begin
        PreClumpDropHelper.begin(null);
        assertTrue(PreClumpDropHelper.isPreClumpingActive(), "Still active during nested explosion");

        // Flush nested
        PreClumpDropHelper.flush(null);
        assertTrue(PreClumpDropHelper.isPreClumpingActive(), "Still active for outer explosion");

        // Flush outer
        PreClumpDropHelper.flush(null);
        assertFalse(PreClumpDropHelper.isPreClumpingActive(), "Inactive after outer flush");
    }

    @Test
    @DisplayName("PreClumpDropHelper: ThreadLocal Context Isolation")
    void testThreadIsolation() throws InterruptedException {
        PreClumpDropHelper.begin(null);
        assertTrue(PreClumpDropHelper.isPreClumpingActive());

        AtomicBoolean otherThreadActive = new AtomicBoolean(true);
        Thread thread = new Thread(() -> {
            otherThreadActive.set(PreClumpDropHelper.isPreClumpingActive());
        });
        thread.start();
        thread.join();

        assertFalse(otherThreadActive.get(), "Other thread must not see main thread's pre-clumping context");
        assertTrue(PreClumpDropHelper.isPreClumpingActive(), "Main thread context remains intact");

        PreClumpDropHelper.clear();
        assertFalse(PreClumpDropHelper.isPreClumpingActive());
    }

    @Test
    @DisplayName("PreClumpDropHelper: AccumulatedDrop Centroid and Saturated Arithmetic")
    void testAccumulatedDropCentroidAndSaturation() {
        PreClumpDropHelper.AccumulatedDrop drop = new PreClumpDropHelper.AccumulatedDrop(
                null, 10, 10.0, 20.0, 30.0
        );

        assertEquals(10, drop.getCount());
        assertEquals(100.0, drop.getSumX(), 1e-6);
        assertEquals(200.0, drop.getSumY(), 1e-6);
        assertEquals(300.0, drop.getSumZ(), 1e-6);
        assertEquals(10.0, drop.getWeight(), 1e-6);

        // Add 10 more items at pos (20, 40, 60)
        drop.add(10, 20.0, 40.0, 60.0);
        assertEquals(20, drop.getCount());
        assertEquals(300.0, drop.getSumX(), 1e-6); // 100 + 200
        assertEquals(600.0, drop.getSumY(), 1e-6); // 200 + 400
        assertEquals(900.0, drop.getSumZ(), 1e-6); // 300 + 600
        assertEquals(20.0, drop.getWeight(), 1e-6);

        // Verify centroid coordinates
        double cx = drop.getSumX() / drop.getWeight();
        double cy = drop.getSumY() / drop.getWeight();
        double cz = drop.getSumZ() / drop.getWeight();
        assertEquals(15.0, cx, 1e-6);
        assertEquals(30.0, cy, 1e-6);
        assertEquals(45.0, cz, 1e-6);

        // Test saturated addition
        drop.add(Integer.MAX_VALUE, 0.0, 0.0, 0.0);
        assertEquals(Integer.MAX_VALUE, drop.getCount(), "Count should saturate at Integer.MAX_VALUE");
    }
}
