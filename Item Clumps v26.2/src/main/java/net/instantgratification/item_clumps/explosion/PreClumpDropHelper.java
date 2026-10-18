// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.explosion;

// Verified against: ServerExplosion.java (26.2+)
// Verified against: Block.java (26.2+)
// Verified against: ItemEntity.java (26.2+)

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.fabricmc.loader.api.FabricLoader;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * PreClumpDropHelper: High-performance drop consolidation for massive explosions.
 * Intercepts block drops during explosion processing and pre-aggregates them into
 * single mega-stacks at their spatial centroid before spawning, cutting entity allocations by up to 90%.
 */
public final class PreClumpDropHelper {

    private static final ThreadLocal<Deque<PreClumpContext>> CONTEXT_STACK = ThreadLocal.withInitial(ArrayDeque::new);

    private PreClumpDropHelper() {}

    public static void begin(ServerLevel level) {
        CONTEXT_STACK.get().push(new PreClumpContext(level));
    }

    public static boolean isPreClumpingActive() {
        Deque<PreClumpContext> deque = CONTEXT_STACK.get();
        return deque != null && !deque.isEmpty();
    }

    public static void accumulate(ItemStack stack, BlockPos pos) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        Deque<PreClumpContext> deque = CONTEXT_STACK.get();
        if (deque != null && !deque.isEmpty()) {
            deque.peek().accumulate(stack, pos);
        }
    }

    public static void flush(ServerLevel level) {
        Deque<PreClumpContext> deque = CONTEXT_STACK.get();
        if (deque != null && !deque.isEmpty()) {
            PreClumpContext context = deque.pop();
            context.flush(level);
            if (deque.isEmpty()) {
                CONTEXT_STACK.remove();
            }
        }
    }

    public static void clear() {
        CONTEXT_STACK.remove();
    }

    public static class PreClumpContext {
        private final ServerLevel level;
        private final List<AccumulatedDrop> drops = new ArrayList<>();

        public PreClumpContext(ServerLevel level) {
            this.level = level;
        }

        public ServerLevel getLevel() {
            return this.level;
        }

        public List<AccumulatedDrop> getDrops() {
            return this.drops;
        }

        public void accumulate(ItemStack stack, BlockPos pos) {
            if (stack == null || stack.isEmpty()) {
                return;
            }
            int count = stack.getCount();
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + 0.5D;

            for (AccumulatedDrop drop : this.drops) {
                if (ItemStack.isSameItemSameComponents(drop.getSampleStack(), stack)) {
                    drop.add(count, x, y, z);
                    return;
                }
            }

            this.drops.add(new AccumulatedDrop(stack.copy(), count, x, y, z));
        }

        public void flush(ServerLevel targetLevel) {
            ServerLevel activeLevel = targetLevel != null ? targetLevel : this.level;
            if (activeLevel == null) {
                return;
            }
            if (!activeLevel.getGameRules().get(GameRules.BLOCK_DROPS)) {
                return;
            }

            int maxClump;
            if (FabricLoader.getInstance().isModLoaded("stack-size-adjuster") || ItemClumpsFabric.MAX_CLUMP_SIZE == null) {
                maxClump = ItemClumpsConfig.get().maxClumpSize;
            } else {
                maxClump = DynamicGameRuleManager.getInt(activeLevel, ItemClumpsFabric.MAX_CLUMP_SIZE);
            }
            if (maxClump <= 0) {
                maxClump = 9999;
            }

            for (AccumulatedDrop drop : this.drops) {
                if (drop.getSampleStack().isEmpty() || drop.getCount() <= 0) {
                    continue;
                }

                double cx = drop.getWeight() > 0 ? (drop.getSumX() / drop.getWeight()) : drop.getSumX();
                double cy = drop.getWeight() > 0 ? (drop.getSumY() / drop.getWeight()) : drop.getSumY();
                double cz = drop.getWeight() > 0 ? (drop.getSumZ() / drop.getWeight()) : drop.getSumZ();

                int remaining = drop.getCount();
                while (remaining > 0) {
                    int chunkSize = Math.min(remaining, maxClump);
                    ItemStack chunkStack = drop.getSampleStack().copyWithCount(chunkSize);
                    ItemEntity itemEntity = new ItemEntity(activeLevel, cx, cy, cz, chunkStack);
                    itemEntity.setDefaultPickUpDelay();
                    activeLevel.addFreshEntity(itemEntity);
                    remaining -= chunkSize;
                }
            }
        }
    }

    public static class AccumulatedDrop {
        private final ItemStack sampleStack;
        private int count;
        private double sumX;
        private double sumY;
        private double sumZ;
        private double weight;

        public AccumulatedDrop(ItemStack sampleStack, int count, double x, double y, double z) {
            this.sampleStack = sampleStack;
            this.count = count;
            this.sumX = x * count;
            this.sumY = y * count;
            this.sumZ = z * count;
            this.weight = count;
        }

        public void add(int addedCount, double x, double y, double z) {
            long newCount = (long) this.count + addedCount;
            this.count = (int) Math.min(newCount, (long) Integer.MAX_VALUE);
            this.sumX += x * addedCount;
            this.sumY += y * addedCount;
            this.sumZ += z * addedCount;
            this.weight += addedCount;
        }

        public ItemStack getSampleStack() {
            return this.sampleStack;
        }

        public int getCount() {
            return this.count;
        }

        public double getSumX() {
            return this.sumX;
        }

        public double getSumY() {
            return this.sumY;
        }

        public double getSumZ() {
            return this.sumZ;
        }

        public double getWeight() {
            return this.weight;
        }
    }
}
