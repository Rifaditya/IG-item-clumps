// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.client.util.VisualScalingHelper;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
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
}
