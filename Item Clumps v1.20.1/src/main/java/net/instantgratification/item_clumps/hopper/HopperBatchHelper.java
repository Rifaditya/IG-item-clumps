// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.hopper;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;

/**
 * Helper for single-slot saturated batch hopper siphoning from item stacks.
 * Adapted for Minecraft 1.20.1 (Legacy LTS / Java 17).
 */
public final class HopperBatchHelper {

    private HopperBatchHelper() {
    }

    /**
     * Determines the maximum number of items that can fit into the first available
     * slot of the target container that can accept the given item stack.
     */
    public static int getFirstSlotCapacity(Container container, ItemStack itemStack) {
        if (container == null || itemStack == null || itemStack.isEmpty()) {
            return 0;
        }
        int maxStack = itemStack.getMaxStackSize();
        int size = container.getContainerSize();
        for (int i = 0; i < size; i++) {
            if (!container.canPlaceItem(i, itemStack)) {
                continue;
            }
            ItemStack slotStack = container.getItem(i);
            int slotMax = Math.min(container.getMaxStackSize(), maxStack);
            if (slotStack.isEmpty()) {
                return slotMax;
            } else if (ItemStack.isSameItemSameTags(slotStack, itemStack)) {
                int space = slotMax - slotStack.getCount();
                if (space > 0) {
                    return space;
                }
            }
        }
        return 0;
    }

    /**
     * Siphons a single-slot saturated batch from the provided entity item stack into the container.
     * Shrinks entityStack in-place by the number of successfully transferred items.
     *
     * @param container      Target container (e.g. Hopper)
     * @param entityStack    Item stack from the ItemEntity
     * @param batchEnabled   Whether batch siphoning is enabled
     * @param batchSize      Max items to siphon per cycle
     * @param minThreshold   Minimum clump count required to trigger batching
     * @return Number of items transferred (0 if failed or full)
     */
    public static int siphonFromStack(
            Container container,
            ItemStack entityStack,
            boolean batchEnabled,
            int batchSize,
            int minThreshold
    ) {
        if (container == null || entityStack == null || entityStack.isEmpty()) {
            return 0;
        }
        int count = entityStack.getCount();
        if (count <= 0) {
            return 0;
        }

        int slotCapacity = getFirstSlotCapacity(container, entityStack);
        if (slotCapacity <= 0) {
            return 0;
        }

        int safeBatchSize = Math.max(1, batchSize);
        int safeThreshold = Math.max(1, minThreshold);

        boolean shouldBatch = batchEnabled && count >= safeThreshold;
        int targetBatch = shouldBatch ? Math.min(count, safeBatchSize) : 1;
        int requestedBatch = Math.min(targetBatch, slotCapacity);

        if (requestedBatch <= 0) {
            return 0;
        }

        ItemStack toInsert = entityStack.copy();
        toInsert.setCount(requestedBatch);
        ItemStack remaining = HopperBlockEntity.addItem(null, container, toInsert, null);
        int remainingCount = remaining.isEmpty() ? 0 : remaining.getCount();
        int inserted = requestedBatch - remainingCount;

        if (inserted > 0) {
            entityStack.shrink(inserted);
        }
        return inserted;
    }
}
