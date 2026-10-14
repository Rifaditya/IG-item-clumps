// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.instantgratification.item_clumps.client.duck.ItemEntityRenderStateDuck;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(ItemEntityRenderState.class)
public class ItemEntityRenderStateMixin implements ItemEntityRenderStateDuck {
    @Unique
    private int item_clumps$count = 1;

    @Override
    public void item_clumps$setCount(int count) {
        this.item_clumps$count = count;
    }

    @Override
    public int item_clumps$getCount() {
        return this.item_clumps$count;
    }
}
