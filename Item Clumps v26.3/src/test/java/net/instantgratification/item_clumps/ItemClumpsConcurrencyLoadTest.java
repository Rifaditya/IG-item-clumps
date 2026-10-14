// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.client.util.VisualScalingHelper;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ItemClumpsConcurrencyLoadTest: Pillar 2 Concurrency & Thread-Safety Load Suite
 *
 * Spawns 50 concurrent worker threads executing simulated clump merge calculations,
 * atomic quotas, and visual scaling lookups under high contention without race conditions or deadlock.
 */
public class ItemClumpsConcurrencyLoadTest {

    private static final int THREAD_COUNT = 50;
    private static final int OPS_PER_THREAD = 400;

    @Test
    @DisplayName("Pillar 2: 50-Thread Concurrent Merge Calculation & Atomic Quota Load Test")
    void testConcurrentMergeAndQuotas() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startLatch = new CountDownLatch(1);
        List<Future<?>> futures = new ArrayList<>(THREAD_COUNT);

        AtomicLong totalItemsProcessed = new AtomicLong(0);
        AtomicInteger totalMerges = new AtomicInteger(0);
        ConcurrentHashMap<Integer, AtomicInteger> itemClumpRegistry = new ConcurrentHashMap<>();

        // Initialize 100 clump keys
        for (int k = 0; k < 100; k++) {
            itemClumpRegistry.put(k, new AtomicInteger(64));
        }

        ItemClumpsConfig config = new ItemClumpsConfig();

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int threadId = t;
            futures.add(pool.submit(() -> {
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    int key = (threadId * 7 + i) % 100;
                    int incomingCount = 1 + (i % 64);

                    // Test concurrent calculation of visual scaling
                    float scale = VisualScalingHelper.calculateScale(incomingCount);
                    assertTrue(scale >= 1.0F && scale <= 2.5F);

                    // Read config values concurrently
                    assertTrue(config.enableClumping);
                    assertTrue(config.maxClumpSize >= 64);

                    // Simulate concurrent merge into atomic clump
                    AtomicInteger clump = itemClumpRegistry.get(key);
                    assertNotNull(clump);

                    clump.updateAndGet(current -> {
                        long sum = (long) current + (long) incomingCount;
                        if (sum > config.maxClumpSize) {
                            return config.maxClumpSize;
                        }
                        return (int) sum;
                    });

                    totalItemsProcessed.addAndGet(incomingCount);
                    totalMerges.incrementAndGet();
                }
            }));
        }

        // Release the latch to start all 50 threads simultaneously
        startLatch.countDown();

        for (Future<?> future : futures) {
            assertDoesNotThrow(() -> future.get(30, TimeUnit.SECONDS),
                    "Concurrent worker threw an exception or deadlocked");
        }

        pool.shutdown();
        assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS), "Thread pool failed to terminate cleanly");

        // Verify total completed operations
        assertEquals(THREAD_COUNT * OPS_PER_THREAD, totalMerges.get(),
                "All concurrent merge operations must complete deterministically");
        assertTrue(totalItemsProcessed.get() > 0, "Processed items must be greater than 0");

        // Verify clump sizes are within bounds
        for (AtomicInteger val : itemClumpRegistry.values()) {
            assertTrue(val.get() >= 64 && val.get() <= config.maxClumpSize,
                    "Clump size must remain bounded: " + val.get());
        }
    }
}
