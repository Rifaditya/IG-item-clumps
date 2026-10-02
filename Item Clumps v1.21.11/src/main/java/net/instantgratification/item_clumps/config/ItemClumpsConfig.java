// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: ItemClumpsConfig.java (1.21.11)
package net.instantgratification.item_clumps.config;

import net.dasik.social.api.config.ConfigHelper;

public class ItemClumpsConfig {
    private static ItemClumpsConfig INSTANCE = new ItemClumpsConfig();
    private static java.nio.file.Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public static synchronized void load(java.nio.file.Path configDir) {
        CONFIG_PATH = configDir.resolve("item-clumps.json");
        INSTANCE = ConfigHelper.loadOrCreate(
                CONFIG_PATH,
                ItemClumpsConfig.class,
                ItemClumpsConfig::new
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        ConfigHelper.save(CONFIG_PATH, INSTANCE);
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
