package net.instantgratification.item_clumps.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Verified against: HopperBlockEntity.java (26.1.2+)
@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Inject(method = "addItem(Lnet/minecraft/world/Container;Lnet/minecraft/world/entity/item/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void item_clumps$customHopperExtract(Container container, ItemEntity entity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = entity.getItem();
        int count = itemStack.getCount();
        if (count > 1) {
            // Entity is a clump. Extract exactly 1 item.
            ItemStack baseItem = itemStack.copy();
            baseItem.setCount(1);
            
            ItemStack result = HopperBlockEntity.addItem(null, container, baseItem, null);
            if (result.isEmpty()) {
                // Hopper successfully absorbed the 1 item. Shrink entity stack.
                ItemStack originalStack = entity.getItem();
                originalStack.shrink(1);
                entity.setItem(originalStack); // Updates standard item tracker and custom name
                cir.setReturnValue(true);
            } else {
                // Hopper was full or couldn't take it
                cir.setReturnValue(false);
            }
        }
    }
}
