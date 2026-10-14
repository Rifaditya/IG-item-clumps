// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.duck;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ItemEntityRenderStateDuck {
    void item_clumps$setCount(int count);
    int item_clumps$getCount();
}
