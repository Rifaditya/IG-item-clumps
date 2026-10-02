// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
// Verified against: ItemClumpsConfig.java (1.21.1)
package net.instantgratification.item_clumps.config;

import net.dasik.social.api.config.ConfigHelper;
import net.instantgratification.item_clumps.ItemClumpsFabric;
import java.nio.file.Path;

public class ItemClumpsConfig {
    private static ItemClumpsConfig INSTANCE = new ItemClumpsConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("item-clumps.json");
        INSTANCE = ConfigHelper.load(
                CONFIG_PATH,
                INSTANCE,
                ItemClumpsConfig.class,
                VERSION,
                cfg -> cfg.configVersion,
                (cfg, ver) -> cfg.configVersion = ver,
                null,
                ItemClumpsFabric.LOGGER
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        ConfigHelper.save(CONFIG_PATH, INSTANCE, ItemClumpsFabric.LOGGER);
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
