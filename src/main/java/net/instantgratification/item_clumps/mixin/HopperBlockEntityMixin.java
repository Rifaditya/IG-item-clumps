package net.instantgratification.item_clumps.mixin;

import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.MegaCountData;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Inject(method = "addItem(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/item/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void item_clumps$customHopperExtract(Container container, ItemEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof MegaCountData data) {
            int megaCount = data.item_clumps$getMegaCount();
            if (megaCount > 1) {
                // Entity is a mega-stack. Extract exactly 1 item.
                ItemStack baseItem = entity.getItem().copy();
                baseItem.setCount(1);
                
                // HopperBlockEntity.addItem(Container from, Container to, ItemStack item, Direction direction)
                ItemStack result = HopperBlockEntity.addItem(null, container, baseItem, null);
                if (result.isEmpty()) {
                    // Hopper successfully absorbed the 1 item
                    data.item_clumps$shrinkMegaCount(1);
                    cir.setReturnValue(true);
                } else {
                    // Hopper was full or couldn't take it
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
