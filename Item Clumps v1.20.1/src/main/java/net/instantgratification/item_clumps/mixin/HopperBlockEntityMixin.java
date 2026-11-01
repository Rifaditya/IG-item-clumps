// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.mixin;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.instantgratification.item_clumps.hopper.HopperBatchHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Verified against: HopperBlockEntity.java (1.20.1)
@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Inject(method = "addItem(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/item/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void item_clumps$customHopperExtract(Container container, ItemEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity == null || container == null) {
            return;
        }

        ItemStack itemStack = entity.getItem();
        if (itemStack == null || itemStack.isEmpty() || itemStack.getCount() <= 0) {
            return;
        }

        boolean batchEnabled;
        int batchSize;
        int minThreshold;

        if (entity.level() != null) {
            batchEnabled = DynamicGameRuleManager.getBoolean(entity.level(), ItemClumpsFabric.BATCH_HOPPER_SIPHONING);
            batchSize = DynamicGameRuleManager.getInt(entity.level(), ItemClumpsFabric.HOPPER_BATCH_SIZE);
            minThreshold = DynamicGameRuleManager.getInt(entity.level(), ItemClumpsFabric.BATCH_SIPHON_MIN_THRESHOLD);
        } else {
            ItemClumpsConfig config = ItemClumpsConfig.get();
            batchEnabled = config.batchHopperSiphoning;
            batchSize = config.hopperBatchSize;
            minThreshold = config.batchSiphonMinThreshold;
        }

        ItemStack originalStack = itemStack.copy();
        int inserted = HopperBatchHelper.siphonFromStack(container, originalStack, batchEnabled, batchSize, minThreshold);

        if (inserted > 0) {
            if (originalStack.isEmpty()) {
                entity.setItem(ItemStack.EMPTY);
                entity.discard();
            } else {
                entity.setItem(originalStack);
            }
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }
}
