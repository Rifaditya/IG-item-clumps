// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: Explosion.java (1.20.1)
package net.instantgratification.item_clumps.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.explosion.PreClumpDropHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Shadow
    @Final
    private Level level;

    @Inject(method = "finalizeExplosion", at = @At("HEAD"))
    private void item_clumps$onFinalizeExplosionHead(boolean spawnParticles, CallbackInfo ci) {
        if (this.level instanceof ServerLevel serverLevel && DynamicGameRuleManager.getBoolean(this.level, ItemClumpsFabric.ENABLE_EXPLOSION_PRE_CLUMPING)) {
            PreClumpDropHelper.begin(serverLevel);
        }
    }

    @Inject(method = "addBlockDrops", at = @At("HEAD"), cancellable = true)
    private static void item_clumps$onAddBlockDrops(ObjectArrayList<?> drops, ItemStack stack, BlockPos pos, CallbackInfo ci) {
        if (PreClumpDropHelper.isPreClumpingActive()) {
            PreClumpDropHelper.accumulate(stack, pos);
            ci.cancel();
        }
    }

    @Inject(method = "finalizeExplosion", at = @At("TAIL"))
    private void item_clumps$onFinalizeExplosionTail(boolean spawnParticles, CallbackInfo ci) {
        if (this.level instanceof ServerLevel serverLevel && PreClumpDropHelper.isPreClumpingActive()) {
            PreClumpDropHelper.flush(serverLevel);
        }
    }
}
