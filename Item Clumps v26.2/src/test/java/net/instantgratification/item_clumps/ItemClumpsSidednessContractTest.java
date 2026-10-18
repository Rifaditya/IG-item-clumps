// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ItemClumpsSidednessContractTest: Pillar 5 Sidedness & Mixin Contract Verification Suite
 *
 * Verifies:
 * - Zero unguarded net.minecraft.client.* or com.mojang.blaze3d.* references in common sources & bytecode.
 * - Complete absence of ModVersionGuard.java and absence of Class.forName in ItemClumpsFabric.java.
 * - Structural integrity and client/common separation of item-clumps.mixins.json and item-clumps.client.mixins.json.
 */
public class ItemClumpsSidednessContractTest {

    @Test
    @DisplayName("Pillar 5: Common Sources & Bytecode Contain Zero Unguarded Client References")
    void testCommonSidednessIsolation() throws IOException {
        Path srcRoot = Paths.get("src/main/java");
        assertTrue(Files.exists(srcRoot), "src/main/java must exist");

        try (Stream<Path> stream = Files.walk(srcRoot)) {
            List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
            assertFalse(javaFiles.isEmpty(), "Java source files must exist");

            for (Path file : javaFiles) {
                String normalized = file.toString().replace('\\', '/');
                String content = Files.readString(file, StandardCharsets.UTF_8);

                assertTrue(content.startsWith("// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3"),
                        "Missing GPLv3 license header on line 1 in: " + normalized);

                // Exclude client package
                if (normalized.contains("/client/")) {
                    continue;
                }

                // Client GUI integration classes in config/ must carry @Environment(EnvType.CLIENT)
                if (content.contains("@Environment(EnvType.CLIENT)")) {
                    continue;
                }

                assertFalse(content.contains("net.minecraft.client."),
                        "Forbidden client package reference in common source file: " + normalized);
                assertFalse(content.contains("com.mojang.blaze3d."),
                        "Forbidden Blaze3D reference in common source file: " + normalized);
            }
        }

        Path classesRoot = Paths.get("build/classes/java/main");
        if (Files.exists(classesRoot)) {
            try (Stream<Path> stream = Files.walk(classesRoot)) {
                List<Path> classFiles = stream.filter(p -> p.toString().endsWith(".class")).toList();
                assertFalse(classFiles.isEmpty(), "build/classes/java/main must contain compiled class files");
                for (Path classFile : classFiles) {
                    String normalized = classFile.toString().replace('\\', '/');
                    if (normalized.contains("/client/")
                            || normalized.contains("ModMenuIntegration")
                            || normalized.contains("YaclScreenHelper")) {
                        continue;
                    }
                    String rawBytes = new String(Files.readAllBytes(classFile), StandardCharsets.ISO_8859_1);
                    assertFalse(rawBytes.contains("net/minecraft/client/"),
                            "Forbidden net/minecraft/client/ constant pool reference in common bytecode: " + normalized);
                    assertFalse(rawBytes.contains("com/mojang/blaze3d/"),
                            "Forbidden com/mojang/blaze3d/ constant pool reference in common bytecode: " + normalized);
                }
            }
        }
    }

    @Test
    @DisplayName("Pillar 5: ModVersionGuard Purged and Zero Unremapped Class.forName in Entrypoint")
    void testModVersionGuardPurged() throws IOException {
        Path guardPath = Paths.get("src/main/java/net/instantgratification/item_clumps/util/ModVersionGuard.java");
        assertFalse(Files.exists(guardPath), "Obsolete ModVersionGuard.java must not exist");

        Path entrypointPath = Paths.get("src/main/java/net/instantgratification/item_clumps/ItemClumpsFabric.java");
        assertTrue(Files.exists(entrypointPath), "ItemClumpsFabric.java must exist");
        String entrypointContent = Files.readString(entrypointPath, StandardCharsets.UTF_8);
        assertFalse(entrypointContent.contains("ModVersionGuard"),
                "ItemClumpsFabric.java must not reference ModVersionGuard");
        assertFalse(entrypointContent.contains("Class.forName"),
                "ItemClumpsFabric.java must not invoke Class.forName");
    }

    @Test
    @DisplayName("Pillar 5: Mixin Configurations Separate Common and Client Mixins Cleanly")
    void testMixinConfigContracts() throws IOException {
        Path commonMixinsPath = Paths.get("src/main/resources/item-clumps.mixins.json");
        Path clientMixinsPath = Paths.get("src/main/resources/item-clumps.client.mixins.json");

        assertTrue(Files.exists(commonMixinsPath), "item-clumps.mixins.json must exist");
        assertTrue(Files.exists(clientMixinsPath), "item-clumps.client.mixins.json must exist");

        String commonJson = Files.readString(commonMixinsPath, StandardCharsets.UTF_8);
        String clientJson = Files.readString(clientMixinsPath, StandardCharsets.UTF_8);

        assertFalse(commonJson.contains("ItemEntityRendererMixin"),
                "Client mixin ItemEntityRendererMixin must not be registered in common mixins array");
        assertFalse(commonJson.contains("ItemEntityRenderStateMixin"),
                "Client mixin ItemEntityRenderStateMixin must not be registered in common mixins array");

        assertTrue(commonJson.contains("ItemEntityMixin"),
                "Common mixin ItemEntityMixin must be registered in common mixins");
        assertTrue(commonJson.contains("HopperBlockEntityMixin"),
                "Common mixin HopperBlockEntityMixin must be registered in common mixins");
        assertTrue(commonJson.contains("ServerExplosionMixin"),
                "Common mixin ServerExplosionMixin must be registered in common mixins");

        assertTrue(clientJson.contains("ItemEntityRendererMixin"),
                "Client mixin ItemEntityRendererMixin must be registered in client mixins");
    }
}
