// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.client.render.ClumpOcclusionCuller;
import net.instantgratification.item_clumps.client.util.VisualScalingHelper;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    @Inject(
        method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER)
    )
    private void item_clumps$applyVisualScale(
        ItemEntity entity,
        float entityYaw,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        CallbackInfo ci
    ) {
        if (entity.getItem() != null && !entity.getItem().isEmpty()) {
            boolean enabled = ItemClumpsConfig.get().enableVisualScaling;
            if (entity.level() != null && ItemClumpsFabric.ENABLE_VISUAL_SCALING != null) {
                enabled = enabled && DynamicGameRuleManager.getBoolean(entity.level(), ItemClumpsFabric.ENABLE_VISUAL_SCALING);
            }
            if (!enabled) {
                return;
            }
            float scale = VisualScalingHelper.calculateScale(entity.getItem().getCount());
            if (scale != 1.0F) {
                poseStack.scale(scale, scale, scale);
            }
        }
    }

    @WrapOperation(
        method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;render(Lnet/minecraft/world/entity/Entity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"
        )
    )
    private void item_clumps$cullOccludedLabel(EntityRenderer instance, Entity entity, float yaw, float tick, PoseStack stack, MultiBufferSource buffer, int light, Operation<Void> original) {
        if (Minecraft.getInstance().gameRenderer != null && Minecraft.getInstance().gameRenderer.getMainCamera() != null && entity.level() != null) {
            Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            if (ClumpOcclusionCuller.isLabelOccluded(entity.level(), camPos, entity.getX(), entity.getY() + 0.75, entity.getZ())) {
                return;
            }
        }
        original.call(instance, entity, yaw, tick, stack, buffer, light);
    }
}
