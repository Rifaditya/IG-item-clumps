// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.client.duck.ItemEntityRenderStateDuck;
import net.instantgratification.item_clumps.client.util.VisualScalingHelper;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;F)V", at = @At("TAIL"))
    private void item_clumps$captureCount(ItemEntity entity, ItemEntityRenderState state, float partialTicks, CallbackInfo ci) {
        if (state instanceof ItemEntityRenderStateDuck duck && entity.getItem() != null) {
            boolean enabled = ItemClumpsConfig.get().enableVisualScaling;
            if (entity.level() != null && ItemClumpsFabric.ENABLE_VISUAL_SCALING != null) {
                enabled = enabled && DynamicGameRuleManager.getBoolean(entity.level(), ItemClumpsFabric.ENABLE_VISUAL_SCALING);
            }
            duck.item_clumps$setCount(enabled ? entity.getItem().getCount() : 1);
        }
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemEntityRenderer;submitMultipleFromCount(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/ItemClusterRenderState;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/phys/AABB;)V"))
    private void item_clumps$applyVisualScale(ItemEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
        if (state instanceof ItemEntityRenderStateDuck duck) {
            int count = duck.item_clumps$getCount();
            float scale = VisualScalingHelper.calculateScale(count);
            if (scale != 1.0F) {
                poseStack.scale(scale, scale, scale);
            }
        }
    }
}
