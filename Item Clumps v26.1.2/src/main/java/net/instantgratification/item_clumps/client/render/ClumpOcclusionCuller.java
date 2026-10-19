// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

@Environment(EnvType.CLIENT)
public class ClumpOcclusionCuller {

    public static boolean isWithinSafetyBubble(Vec3 cameraPos, double targetX, double targetY, double targetZ) {
        if (cameraPos == null) return false;
        double dx = cameraPos.x - targetX;
        double dy = cameraPos.y - targetY;
        double dz = cameraPos.z - targetZ;
        return (dx * dx + dy * dy + dz * dz) <= 2.25; // 1.5m safety bubble
    }

    public static boolean isLabelOccluded(Level level, Vec3 cameraPos, double targetX, double targetY, double targetZ) {
        if (!ItemClumpsConfig.get().cullOccludedLabels) return false;
        if (level == null || cameraPos == null) return false;
        if (isWithinSafetyBubble(cameraPos, targetX, targetY, targetZ)) return false;

        Vec3 target = new Vec3(targetX, targetY, targetZ);
        ClipContext ctx = new ClipContext(cameraPos, target, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult hit = level.clip(ctx);
        if (hit.getType() == HitResult.Type.MISS) return false;

        BlockState state = level.getBlockState(hit.getBlockPos());
        return state.canOcclude() && state.isSolidRender();
    }
}
