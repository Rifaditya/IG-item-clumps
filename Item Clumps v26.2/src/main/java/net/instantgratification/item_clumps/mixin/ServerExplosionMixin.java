// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: ServerExplosion.java (26.2+)
package net.instantgratification.item_clumps.mixin;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.explosion.PreClumpDropHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class ServerExplosionMixin {

    @Shadow
    @Final
    private ServerLevel level;

    @Inject(method = "interactWithBlocks", at = @At("HEAD"))
    private void item_clumps$onInteractWithBlocksHead(List<BlockPos> targetBlocks, CallbackInfo ci) {
        if (DynamicGameRuleManager.getBoolean(this.level, ItemClumpsFabric.ENABLE_EXPLOSION_PRE_CLUMPING)) {
            PreClumpDropHelper.begin(this.level);
        }
    }

    @Inject(method = "addOrAppendStack", at = @At("HEAD"), cancellable = true)
    private static void item_clumps$onAddOrAppendStack(List<?> stacks, ItemStack stack, BlockPos pos, CallbackInfo ci) {
        if (PreClumpDropHelper.isPreClumpingActive()) {
            PreClumpDropHelper.accumulate(stack, pos);
            ci.cancel();
        }
    }

    @Inject(method = "interactWithBlocks", at = @At("TAIL"))
    private void item_clumps$onInteractWithBlocksTail(List<BlockPos> targetBlocks, CallbackInfo ci) {
        if (PreClumpDropHelper.isPreClumpingActive()) {
            PreClumpDropHelper.flush(this.level);
        }
    }
}
