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

import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.util.Objects;

// Verified against: ItemEntity.java (26.1.2+)
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow public abstract ItemStack getItem();
    @Shadow public abstract void setItem(ItemStack itemStack);
    @Shadow private java.util.UUID target;
    @Shadow private int pickupDelay;
    @Shadow private int age;

    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    private void item_clumps$updateVanillaNameTag() {
        if (this.level() == null || this.level().isClientSide()) return;
        ItemStack stack = this.getItem();
        if (stack.isEmpty()) {
            this.setCustomName(null);
            this.setCustomNameVisible(false);
            return;
        }

        int count = stack.getCount();
        int labelMin = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.LABEL_MIN_COUNT);
        int maxStack = (labelMin == -1) ? stack.getOrDefault(DataComponents.MAX_STACK_SIZE, 1) : labelMin;
        if (DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.RENDER_LABELS) && count > maxStack) {
            Component name = stack.getItemName().copy().append(" x" + count);
            if (!this.hasCustomName() || !this.getCustomName().getString().equals(name.getString())) {
                this.setCustomName(name);
                this.setCustomNameVisible(true);
            }
        } else {
            if (this.hasCustomName()) {
                this.setCustomName(null);
                this.setCustomNameVisible(false);
            }
        }
    }

    @Inject(method = "setItem", at = @At("TAIL"))
    private void item_clumps$onSetItem(ItemStack itemStack, CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            this.item_clumps$updateVanillaNameTag();
        }
    }

    @Inject(method = "isMergable", at = @At("HEAD"), cancellable = true)
    private void item_clumps$customIsMergable(CallbackInfoReturnable<Boolean> cir) {
        if (DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) {
            int maxClump;
            if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("stack-size-adjuster") || ItemClumpsFabric.MAX_CLUMP_SIZE == null) {
                maxClump = this.getItem().getMaxStackSize();
            } else {
                maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);
            }
            boolean mergable = this.isAlive() 
                && this.pickupDelay != Short.MAX_VALUE 
                && this.age != Short.MIN_VALUE 
                && this.age < 6000 
                && this.getItem().getCount() < maxClump;
            cir.setReturnValue(mergable);
        }
    }

    @Redirect(method = "mergeWithNeighbours", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
    private net.minecraft.world.phys.AABB item_clumps$customInflate(net.minecraft.world.phys.AABB boundingBox, double x, double y, double z) {
        if (DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) {
            double radius = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MERGE_RADIUS);
            return boundingBox.inflate(radius, y, radius);
        }
        return boundingBox.inflate(x, y, z);
    }

    @Inject(method = "tryToMerge", at = @At("HEAD"), cancellable = true)
    private void item_clumps$customMerge(ItemEntity other, CallbackInfo ci) {
        ItemStack thisStack = this.getItem();
        ItemStack otherStack = other.getItem();

        if (!Objects.equals(this.target, ((ItemEntityMixin)(Object)other).target) || !ItemStack.isSameItemSameComponents(thisStack, otherStack)) {
            return;
        }

        if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) return;

        if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("magnet")) {
            try {
                java.lang.reflect.Method isMagnetizedMethod;
                try {
                    isMagnetizedMethod = this.getClass().getMethod("ig_magnet$isMagnetized");
                } catch (NoSuchMethodException e) {
                    isMagnetizedMethod = this.getClass().getMethod("ig$isMagnetized");
                }
                if ((boolean) isMagnetizedMethod.invoke(this) || (boolean) isMagnetizedMethod.invoke(other)) {
                    ci.cancel();
                    return;
                }
            } catch (Throwable ignored) {}
        }

        int thisCount = thisStack.getCount();
        int otherCount = otherStack.getCount();
        int maxClump;
        if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("stack-size-adjuster") || ItemClumpsFabric.MAX_CLUMP_SIZE == null) {
            maxClump = thisStack.getMaxStackSize();
        } else {
            maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);
        }

        long sum = (long) thisCount + (long) otherCount;
        if (sum > (long) maxClump) {
            int spaceLeft = maxClump - thisCount;
            if (spaceLeft > 0) {
                ItemStack thisCopy = thisStack.copyWithCount(maxClump);
                this.setItem(thisCopy);
                
                ItemStack otherCopy = otherStack.copyWithCount(otherCount - spaceLeft);
                other.setItem(otherCopy);

                this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
                this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
            }
            ci.cancel();
            return;
        }

        // Full Merge: larger stack absorbs the smaller stack
        if (otherCount < thisCount) {
            ItemStack thisCopy = thisStack.copyWithCount((int) sum);
            this.setItem(thisCopy);
            this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
            this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
            other.discard();
        } else {
            ItemStack otherCopy = otherStack.copyWithCount((int) sum);
            other.setItem(otherCopy);
            ((ItemEntityMixin)(Object)other).pickupDelay = Math.max(((ItemEntityMixin)(Object)other).pickupDelay, this.pickupDelay);
            ((ItemEntityMixin)(Object)other).age = Math.min(((ItemEntityMixin)(Object)other).age, this.age);
            this.discard();
        }
        ci.cancel();
    }

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void item_clumps$smartPickup(Player player, CallbackInfo ci) {
        if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING) || this.level().isClientSide()) return;

        int count = this.getItem().getCount();
        if (count <= 1) return; // Let vanilla handle normal 1-count items to avoid edge cases

        if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID()))) {
            ItemStack pickupStack = this.getItem().copy();
            int originalCount = count;

            player.getInventory().add(pickupStack);
            int added = originalCount - pickupStack.getCount();

            if (added > 0) {
                player.take(this, added);
                player.awardStat(net.minecraft.stats.Stats.ITEM_PICKED_UP.get(pickupStack.getItem()), added);

                ItemStack remainingStack = this.getItem().copyWithCount(pickupStack.getCount());
                this.setItem(remainingStack);

                if (pickupStack.getCount() <= 0) {
                    player.onItemPickup((ItemEntity) (Object) this);
                }
            }
            ci.cancel(); // Always cancel if we processed a mega stack to prevent vanilla logic interfering
        }
    }
}
