package net.instantgratification.item_clumps.mixin;

import net.instantgratification.item_clumps.MegaCountData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Verified against: ClientboundTakeItemEntityPacket.java (26.1 Snapshot 11+)
// Verified against: ClientPacketListener.java (26.1 Snapshot 11+)
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {

    @Shadow private ClientLevel level;

    @Inject(method = "handleTakeItemEntity(Lnet/minecraft/network/protocol/game/ClientboundTakeItemEntityPacket;)V", at = @At("HEAD"))
    private void item_clumps$preventClientDiscard(ClientboundTakeItemEntityPacket packet, CallbackInfo ci) {
        if (this.level == null) {
            return;
        }
        Entity from = this.level.getEntity(packet.getItemId());
        if (from instanceof ItemEntity itemEntity && itemEntity instanceof MegaCountData data) {
            int megaCount = data.item_clumps$getMegaCount();
            if (megaCount > 0) {
                ItemStack itemStack = itemEntity.getItem();
                if (!itemStack.isEmpty()) {
                    // Temporarily set the client-side item stack count so that
                    // the shrink operation in handleTakeItemEntity does not reduce it to <= 0.
                    itemStack.setCount(megaCount + packet.getAmount());
                }
            }
        }
    }

    @Inject(method = "handleTakeItemEntity(Lnet/minecraft/network/protocol/game/ClientboundTakeItemEntityPacket;)V", at = @At("TAIL"))
    private void item_clumps$restoreClientCount(ClientboundTakeItemEntityPacket packet, CallbackInfo ci) {
        if (this.level == null) {
            return;
        }
        Entity from = this.level.getEntity(packet.getItemId());
        if (from instanceof ItemEntity itemEntity && itemEntity instanceof MegaCountData) {
            ItemStack itemStack = itemEntity.getItem();
            if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
                // Restore the base count to 1 for rendering/logic consistency
                itemStack.setCount(1);
            }
        }
    }
}
