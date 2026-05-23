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

    public static ItemClumpsConfig get() {
        return INSTANCE;
    }
}
