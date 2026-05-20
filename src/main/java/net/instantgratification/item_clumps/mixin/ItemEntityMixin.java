package net.instantgratification.item_clumps.mixin;

import net.instantgratification.item_clumps.ItemClumpsFabric;
import net.instantgratification.item_clumps.MegaCountData;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import java.util.Objects;

// Verified against: E:\Minecraft Project\Minecraft Decomplide code for reference only\26.1 .2 releast decompile\client\src\net\minecraft\world\entity\item\ItemEntity.java
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements MegaCountData {

    @Shadow public abstract ItemStack getItem();
    @Shadow public abstract void setItem(ItemStack itemStack);
    @Shadow private java.util.UUID target;
    @Shadow private int pickupDelay;
    @Shadow private int age;

    private static final EntityDataAccessor<Integer> MEGA_COUNT = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SYNCED_RENDER_LABELS = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.BOOLEAN);

    public ItemEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void item_clumps$defineMegaCount(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(MEGA_COUNT, 1);
        builder.define(SYNCED_RENDER_LABELS, true);
    }

    @Override
    public int item_clumps$getMegaCount() {
        return this.getEntityData().get(MEGA_COUNT);
    }

    @Override
    public boolean item_clumps$shouldRenderLabels() {
        return this.getEntityData().get(SYNCED_RENDER_LABELS);
    }

    @Override
    public void item_clumps$setMegaCount(int count) {
        this.getEntityData().set(MEGA_COUNT, count);
        if (count <= 0) {
            this.discard();
        }
    }

    @Override
    public void item_clumps$addMegaCount(int count) {
        this.item_clumps$setMegaCount(this.item_clumps$getMegaCount() + count);
    }

    @Override
    public void item_clumps$shrinkMegaCount(int count) {
        this.item_clumps$setMegaCount(this.item_clumps$getMegaCount() - count);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void item_clumps$saveMegaCount(ValueOutput output, CallbackInfo ci) {
        output.putInt("mega_count", this.item_clumps$getMegaCount());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void item_clumps$readMegaCount(ValueInput input, CallbackInfo ci) {
        int loadedCount = input.getIntOr("mega_count", 1);
        this.item_clumps$setMegaCount(loadedCount);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void item_clumps$tickRenderLabelsSync(CallbackInfo ci) {
        if (this.level() != null && !this.level().isClientSide()) {
            boolean currentVal = DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.RENDER_LABELS);
            if (this.getEntityData().get(SYNCED_RENDER_LABELS) != currentVal) {
                this.getEntityData().set(SYNCED_RENDER_LABELS, currentVal);
            }
        }
    }

    @Inject(method = "setItem", at = @At("HEAD"))
    private void item_clumps$onSetItem(ItemStack itemStack, CallbackInfo ci) {
        // If an item stack is natively larger than 1, we absorb its count into mega_count
        // and clamp the real stack to 1. This handles initial spawns and commands.
        if (this.level() != null && !this.level().isClientSide() && DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING)) {
            if (itemStack != null && !itemStack.isEmpty()) {
                int count = itemStack.getCount();
                if (count > 1) {
                    this.item_clumps$addMegaCount(count - 1);
                    itemStack.setCount(1);
                }
            }
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

        ItemEntity thisEntity = (ItemEntity)(Object)this;
        ItemStack thisStack = this.getItem();
        ItemStack otherStack = other.getItem();

        if (!Objects.equals(this.target, ((ItemEntityMixin)(Object)other).target) || !ItemEntity.areMergable(thisStack, otherStack)) {
            return;
        }

        MegaCountData otherData = (MegaCountData) other;
        int thisMega = this.item_clumps$getMegaCount();
        int otherMega = otherData.item_clumps$getMegaCount();
        int maxClump = DynamicGameRuleManager.getInt(this.level(), ItemClumpsFabric.MAX_CLUMP_SIZE);

        if (thisMega + otherMega > maxClump) {
            // Cannot merge fully, fill this clump to max and shrink the other
            int spaceLeft = maxClump - thisMega;
            if (spaceLeft > 0) {
                this.item_clumps$addMegaCount(spaceLeft);
                otherData.item_clumps$shrinkMegaCount(spaceLeft);
            }
            ci.cancel();
            return;
        }

        // Full Merge
        // The one with larger mega count absorbs the smaller one to minimize moving data
        if (otherMega < thisMega) {
            this.item_clumps$addMegaCount(otherMega);
            this.pickupDelay = Math.max(this.pickupDelay, ((ItemEntityMixin)(Object)other).pickupDelay);
            this.age = Math.min(this.age, ((ItemEntityMixin)(Object)other).age);
            other.discard();
        } else {
            otherData.item_clumps$addMegaCount(thisMega);
            ((ItemEntityMixin)(Object)other).pickupDelay = Math.max(((ItemEntityMixin)(Object)other).pickupDelay, this.pickupDelay);
            ((ItemEntityMixin)(Object)other).age = Math.min(((ItemEntityMixin)(Object)other).age, this.age);
            this.discard();
        }
        ci.cancel();
    }

    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void item_clumps$smartPickup(Player player, CallbackInfo ci) {
        if (!DynamicGameRuleManager.getBoolean(this.level(), ItemClumpsFabric.ENABLE_CLUMPING) || this.level().isClientSide()) return;

        int megaCount = this.item_clumps$getMegaCount();
        if (megaCount <= 1) return; // Let vanilla handle normal 1-count items to avoid edge cases

        ItemStack baseItem = this.getItem().copy();
        baseItem.setCount(1); // Ensure base is 1

        if (this.pickupDelay == 0 && (this.target == null || this.target.equals(player.getUUID()))) {
            int originalMegaCount = megaCount;
            int maxStack = baseItem.getMaxStackSize();
            
            while (megaCount > 0) {
                int toTake = Math.min(megaCount, maxStack);
                ItemStack chunk = baseItem.copy();
                chunk.setCount(toTake);

                player.getInventory().add(chunk);
                int added = toTake - chunk.getCount();

                if (added > 0) {
                    megaCount -= added;
                    player.take(this, added);
                    player.awardStat(net.minecraft.stats.Stats.ITEM_PICKED_UP.get(baseItem.getItem()), added);
                }

                if (!chunk.isEmpty()) {
                    // Player inventory is full, cannot pick up any more items in this chunk
                    break;
                }
            }

            if (megaCount != originalMegaCount) {
                this.item_clumps$setMegaCount(megaCount);
                if (megaCount <= 0) {
                    player.onItemPickup((ItemEntity) (Object) this);
                }
            }
            ci.cancel(); // Always cancel if we processed a mega stack to prevent vanilla logic interfering
        }
    }
}
