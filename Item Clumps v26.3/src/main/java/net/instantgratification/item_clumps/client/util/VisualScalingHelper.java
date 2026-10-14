// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class VisualScalingHelper {
    private VisualScalingHelper() {}

    public static float calculateScale(int count) {
        if (count <= 1) {
            return 1.0F;
        }
        float minScale = 1.0F;
        float maxScale = 2.5F;
        float k = 0.3F;
        double log = Math.log10(count);
        float raw = (float) (minScale + k * log);
        return Math.min(maxScale, Math.max(minScale, raw));
    }
}
