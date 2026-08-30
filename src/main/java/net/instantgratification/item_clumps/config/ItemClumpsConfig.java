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
// Verified against: ItemClumpsConfig.java (26.1.2+)
package net.instantgratification.item_clumps.config;

public class ItemClumpsConfig {
    private static ItemClumpsConfig INSTANCE = new ItemClumpsConfig();
    private static final com.google.gson.Gson GSON = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
    private static java.nio.file.Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public static synchronized void load(java.nio.file.Path configDir) {
        CONFIG_PATH = configDir.resolve("item-clumps.json");
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("Item Clumps");
        
        if (!java.nio.file.Files.exists(CONFIG_PATH)) {
            logger.info("No config found, generating default config");
            save();
            return;
        }

        try {
            long size = java.nio.file.Files.size(CONFIG_PATH);
            if (size > 1024 * 1024) {
                logger.error("Config file too large ({} bytes). Using defaults for safety!", size);
                return;
            }

            try (java.io.Reader reader = java.nio.file.Files.newBufferedReader(CONFIG_PATH, java.nio.charset.StandardCharsets.UTF_8)) {
                ItemClumpsConfig tempInstance = GSON.fromJson(reader, ItemClumpsConfig.class);
                if (tempInstance != null) {
                    INSTANCE = tempInstance;
                    save(); // Write back to ensure new fields are saved
                }
            }
        } catch (Exception e) {
            logger.error("Critical error loading config. Preserving file and using defaults.", e);
        }
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("Item Clumps");
        
        try {
            java.nio.file.Files.createDirectories(CONFIG_PATH.getParent());
            java.nio.file.Path tempPath = CONFIG_PATH.resolveSibling(CONFIG_PATH.getFileName().toString() + ".tmp");
            
            try (java.io.Writer writer = java.nio.file.Files.newBufferedWriter(tempPath, java.nio.charset.StandardCharsets.UTF_8)) {
                GSON.toJson(INSTANCE, writer);
            }

            try {
                java.nio.file.Files.move(tempPath, CONFIG_PATH, java.nio.file.StandardCopyOption.REPLACE_EXISTING, java.nio.file.StandardCopyOption.ATOMIC_MOVE);
            } catch (java.io.IOException e) {
                java.nio.file.Files.move(tempPath, CONFIG_PATH, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            logger.error("Failed to save config safely!", e);
        }
    }

    public boolean enableClumping = true;
    public int maxClumpSize = 9999;
    public boolean renderLabels = true;
    public int mergeRadius = 1;
    public int labelMinCount = -1;

    public static ItemClumpsConfig get() {
        return INSTANCE;
    }
}
