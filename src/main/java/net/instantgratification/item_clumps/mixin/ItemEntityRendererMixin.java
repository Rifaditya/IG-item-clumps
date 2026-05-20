package net.instantgratification.item_clumps.mixin;

import net.instantgratification.item_clumps.MegaCountData;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Verified against: E:\Minecraft Project\Minecraft Decomplide code for reference only\26.1 .2 releast decompile\client\src\net\minecraft\client\renderer\entity\ItemEntityRenderer.java
@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;F)V", at = @At("TAIL"))
    private void item_clumps$injectCustomNameTag(ItemEntity entity, ItemEntityRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof MegaCountData data) {
            if (!data.item_clumps$shouldRenderLabels()) {
                return;
            }
            int megaCount = data.item_clumps$getMegaCount();
            int maxStack = entity.getItem().getMaxStackSize();
            if (megaCount > maxStack) {
                if (state.distanceToCameraSq < 4096.0) { // 64 blocks radius
                    Component itemName = entity.getItem().getItemName();
                    state.nameTag = Component.literal("").append(itemName).append(" x" + megaCount);
                    if (state.nameTagAttachment == null) {
                        state.nameTagAttachment = new Vec3(0, entity.getBbHeight() + 0.5f, 0);
                    }
                }
            }
        }
    }
}
