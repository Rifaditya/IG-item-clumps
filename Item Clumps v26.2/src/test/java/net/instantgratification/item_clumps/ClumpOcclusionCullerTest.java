// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import net.instantgratification.item_clumps.client.render.ClumpOcclusionCuller;
import net.instantgratification.item_clumps.config.ItemClumpsConfig;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClumpOcclusionCullerTest {

    private boolean originalCullState = ItemClumpsConfig.get().cullOccludedLabels;

    @AfterEach
    void tearDown() {
        ItemClumpsConfig.get().cullOccludedLabels = originalCullState;
    }

    @Test
    @DisplayName("ClumpOcclusionCuller: Null level or cameraPos safely returns false")
    void testNullChecks() {
        Vec3 cameraPos = new Vec3(5.0, 5.0, 5.0);

        // Null level
        assertFalse(ClumpOcclusionCuller.isLabelOccluded(null, cameraPos, 10.0, 10.0, 10.0),
                "Null level must safely return false");

        // Null camera
        assertFalse(ClumpOcclusionCuller.isLabelOccluded(null, null, 10.0, 10.0, 10.0),
                "Null camera must safely return false");
    }

    @Test
    @DisplayName("ClumpOcclusionCuller: Config toggle disables culling checks")
    void testConfigToggle() {
        ItemClumpsConfig.get().cullOccludedLabels = false;

        Vec3 cameraPos = new Vec3(5.0, 5.0, 5.0);
        assertFalse(ClumpOcclusionCuller.isLabelOccluded(null, cameraPos, 10.0, 10.0, 10.0),
                "When cullOccludedLabels is false, isLabelOccluded must return false");
    }

    @Test
    @DisplayName("ClumpOcclusionCuller: Proximity safety bubble (< 1.5m) prevents label culling")
    void testSafetyBubble() {
        Vec3 cameraPos = new Vec3(0.0, 0.0, 0.0);

        // Within 1.5m radius (distSq <= 2.25)
        assertTrue(ClumpOcclusionCuller.isWithinSafetyBubble(cameraPos, 1.0, 0.0, 0.0),
                "1.0m should be within safety bubble");
        assertTrue(ClumpOcclusionCuller.isWithinSafetyBubble(cameraPos, 0.0, 1.5, 0.0),
                "Exact 1.5m boundary should be within safety bubble");
        assertTrue(ClumpOcclusionCuller.isWithinSafetyBubble(cameraPos, 0.5, 0.5, 0.5),
                "0.866m diagonal should be within safety bubble");

        // Outside 1.5m radius (distSq > 2.25)
        assertFalse(ClumpOcclusionCuller.isWithinSafetyBubble(cameraPos, 1.51, 0.0, 0.0),
                "1.51m should be outside safety bubble");
        assertFalse(ClumpOcclusionCuller.isWithinSafetyBubble(cameraPos, 5.0, 0.0, 0.0),
                "5.0m should be outside safety bubble");

        // Null camera safety
        assertFalse(ClumpOcclusionCuller.isWithinSafetyBubble(null, 0.0, 0.0, 0.0),
                "Null camera should return false for safety bubble check");
    }
}
