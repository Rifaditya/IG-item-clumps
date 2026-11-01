// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.hopper.HopperBatchHelper;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HopperBatchSiphoningTest: Verifies single-slot saturated batch hopper siphoning
 * and enforces the 6 Negative Parity invariants defined in BL-CLUMP-005.
 * Adapted for Minecraft 1.20.1 (Legacy LTS / Java 17).
 */
public class HopperBatchSiphoningTest {

    @BeforeAll
    static void initMinecraft() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Pillar 4: Positive Batch Transfer - 2000 Clump with Batch Enabled Siphons 16 Items")
    void testPositiveBatchTransfer() {
        SimpleContainer container = new SimpleContainer(5);
        ItemStack entityStack = new ItemStack(Items.COBBLESTONE, 2000);

        int siphoned = HopperBatchHelper.siphonFromStack(container, entityStack, true, 16, 64);

        assertEquals(16, siphoned, "Must siphon exactly 16 items per cycle");
        assertEquals(1984, entityStack.getCount(), "Entity stack must shrink by 16");
        assertEquals(16, container.getItem(0).getCount(), "Hopper container must receive 16 items in slot 0");
        assertTrue(container.getItem(1).isEmpty(), "Slot 1 must remain empty (single-slot saturation)");
    }

    @Test
    @DisplayName("Negative Parity: 63 Count Below Threshold 64 Siphons Strictly 1 Item")
    void testNegativeParityBelowThreshold() {
        SimpleContainer container = new SimpleContainer(5);
        ItemStack entityStack = new ItemStack(Items.COBBLESTONE, 63);

        int siphoned = HopperBatchHelper.siphonFromStack(container, entityStack, true, 16, 64);

        assertEquals(1, siphoned, "Must siphon strictly 1 item when below threshold 64");
        assertEquals(62, entityStack.getCount(), "Entity stack must shrink by 1");
        assertEquals(1, container.getItem(0).getCount(), "Hopper container must receive strictly 1 item");
        assertTrue(container.getItem(1).isEmpty(), "Slot 1 must remain empty");
    }

    @Test
    @DisplayName("Negative Parity: 2000 Count with Batch Disabled Siphons Strictly 1 Item")
    void testNegativeParityBatchDisabled() {
        SimpleContainer container = new SimpleContainer(5);
        ItemStack entityStack = new ItemStack(Items.COBBLESTONE, 2000);

        int siphoned = HopperBatchHelper.siphonFromStack(container, entityStack, false, 16, 64);

        assertEquals(1, siphoned, "Must siphon strictly 1 item when batching is disabled");
        assertEquals(1999, entityStack.getCount(), "Entity stack must shrink by 1");
        assertEquals(1, container.getItem(0).getCount(), "Hopper container must receive strictly 1 item");
        assertTrue(container.getItem(1).isEmpty(), "Slot 1 must remain empty");
    }

    @Test
    @DisplayName("Negative Parity: 1 Item Drop (Payment Coin) Siphons Strictly 1 Item and Becomes Empty")
    void testNegativeParitySingleCoin() {
        SimpleContainer container = new SimpleContainer(5);
        ItemStack entityStack = new ItemStack(Items.GOLD_NUGGET, 1);

        int siphoned = HopperBatchHelper.siphonFromStack(container, entityStack, true, 16, 64);

        assertEquals(1, siphoned, "Must siphon strictly 1 coin");
        assertEquals(0, entityStack.getCount(), "Entity stack must shrink to 0");
        assertTrue(entityStack.isEmpty(), "Entity stack must be marked empty");
        assertEquals(1, container.getItem(0).getCount(), "Hopper container must receive strictly 1 coin");
    }

    @Test
    @DisplayName("Slot Saturation: Slot Capacity 5 with Batch 16 Siphons Strictly 5 Items")
    void testSlotSaturationTransfer() {
        SimpleContainer container = new SimpleContainer(1);
        container.setItem(0, new ItemStack(Items.COBBLESTONE, 59));
        ItemStack entityStack = new ItemStack(Items.COBBLESTONE, 2000);

        int siphoned = HopperBatchHelper.siphonFromStack(container, entityStack, true, 16, 64);

        assertEquals(5, siphoned, "Must siphon strictly 5 items to saturate the slot");
        assertEquals(1995, entityStack.getCount(), "Entity stack must shrink by 5");
        assertEquals(64, container.getItem(0).getCount(), "Slot must be saturated at exactly 64");
    }

    @Test
    @DisplayName("Stack Limit: Non-Stackable Item (Diamond Sword) Siphons Strictly 1 Item")
    void testStackLimitNonStackable() {
        SimpleContainer container = new SimpleContainer(5);
        ItemStack entityStack = new ItemStack(Items.DIAMOND_SWORD, 1);

        int siphoned = HopperBatchHelper.siphonFromStack(container, entityStack, true, 16, 64);

        assertEquals(1, siphoned, "Must siphon strictly 1 sword");
        assertEquals(0, entityStack.getCount(), "Entity stack must shrink to 0");
        assertTrue(entityStack.isEmpty(), "Entity stack must be marked empty");
        assertEquals(1, container.getItem(0).getCount(), "Hopper container must receive 1 sword");

        // Clumped swords with count 10 and threshold 1
        ItemStack clumpedSwords = new ItemStack(Items.DIAMOND_SWORD, 10);
        SimpleContainer container2 = new SimpleContainer(5);
        int siphoned2 = HopperBatchHelper.siphonFromStack(container2, clumpedSwords, true, 16, 1);
        assertEquals(1, siphoned2, "Must never siphon >1 non-stackable item into a single slot");
        assertEquals(9, clumpedSwords.getCount());
        assertEquals(1, container2.getItem(0).getCount());
    }
}
