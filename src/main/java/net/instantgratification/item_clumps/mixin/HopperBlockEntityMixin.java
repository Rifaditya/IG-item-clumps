/*
 * Copyright (C) 2026 Rifaditya (Dasik)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
            ItemStack baseItem = itemStack.copyWithCount(1);
            
            ItemStack result = HopperBlockEntity.addItem(null, container, baseItem, null);
            if (result.isEmpty()) {
                // Hopper successfully absorbed the 1 item. Shrink entity stack.
                ItemStack originalStack = entity.getItem().copy();
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
