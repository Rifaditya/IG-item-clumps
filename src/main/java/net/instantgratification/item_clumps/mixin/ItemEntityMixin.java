package net.instantgratification.item_clumps.mixin;

import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
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
        int maxStack = stack.getMaxStackSize();
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

    @Inject(method = "tick", at = @At("HEAD"))
    private void item_clumps$onTick(CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            this.item_clumps$updateVanillaNameTag();
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
            int maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);
            boolean mergable = this.isAlive() 
                && this.pickupDelay != Short.MAX_VALUE 
                && this.age != Short.MIN_VALUE 
                && this.age < 6000 
                && this.getItem().getCount() < maxClump;
            cir.setReturnValue(mergable);
        }
    }

    @ModifyArgs(method = "mergeWithNeighbours", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;inflate(DDD)Lnet/minecraft/world/phys/AABB;"))
    private void item_clumps$modifySearchRadius(Args args) {
        if (DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) {
            double radius = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MERGE_RADIUS);
            args.set(0, radius);
            args.set(2, radius);
        }
    }

    @Inject(method = "tryToMerge", at = @At("HEAD"), cancellable = true)
    private void item_clumps$customMerge(ItemEntity other, CallbackInfo ci) {
        if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) return;

        ItemStack thisStack = this.getItem();
        ItemStack otherStack = other.getItem();

        if (!Objects.equals(this.target, ((ItemEntityMixin)(Object)other).target) || !ItemStack.isSameItemSameComponents(thisStack, otherStack)) {
            return;
        }

        int thisCount = thisStack.getCount();
        int otherCount = otherStack.getCount();
        int maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);

        if (thisCount + otherCount > maxClump) {
            int spaceLeft = maxClump - thisCount;
            if (spaceLeft > 0) {
                ItemStack thisCopy = thisStack.copy();
                thisCopy.setCount(maxClump);
                this.setItem(thisCopy);
                
                ItemStack otherCopy = otherStack.copy();
                otherCopy.setCount(otherCount - spaceLeft);
                other.setItem(otherCopy);
            }
            ci.cancel();
            return;
        }

        // Full Merge: larger stack absorbs the smaller stack
        if (otherCount < thisCount) {
            ItemStack thisCopy = thisStack.copy();
            thisCopy.setCount(thisCount + otherCount);
            this.setItem(thisCopy);
            this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
            this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
            other.discard();
        } else {
            ItemStack otherCopy = otherStack.copy();
            otherCopy.setCount(thisCount + otherCount);
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

        ItemStack baseItem = this.getItem().copy();
        baseItem.setCount(1); // Ensure base count is 1 for simulation

        if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID()))) {
            int originalCount = count;
            int maxStack = baseItem.getMaxStackSize();
            
            while (count > 0) {
                int toTake = Math.min(count, maxStack);
                ItemStack chunk = baseItem.copy();
                chunk.setCount(toTake);

                player.getInventory().add(chunk);
                int added = toTake - chunk.getCount();

                if (added > 0) {
                    count -= added;
                    player.take(this, added);
                    player.awardStat(net.minecraft.stats.Stats.ITEM_PICKED_UP.get(baseItem.getItem()), added);
                }

                if (!chunk.isEmpty()) {
                    // Player inventory is full
                    break;
                }
            }

            if (count != originalCount) {
                ItemStack stack = this.getItem().copy();
                stack.setCount(count);
                this.setItem(stack);
                if (count <= 0) {
                    player.onItemPickup((ItemEntity) (Object) this);
                }
            }
            ci.cancel(); // Always cancel if we processed a mega stack to prevent vanilla logic interfering
        }
    }
}
