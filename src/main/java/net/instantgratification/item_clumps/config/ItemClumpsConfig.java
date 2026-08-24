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
    private static java.nio.file.Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public static synchronized void load(java.nio.file.Path configDir) {
        CONFIG_PATH = configDir.resolve("item-clumps.json");
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("Item Clumps");
        INSTANCE = net.dasik.social.api.config.ConfigHelper.load(
                CONFIG_PATH,
                INSTANCE,
                ItemClumpsConfig.class,
                VERSION,
                config -> config.configVersion,
                (config, ver) -> config.configVersion = ver,
                null,
                logger
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        net.dasik.social.api.config.ConfigHelper.save(
                CONFIG_PATH,
                INSTANCE,
                org.slf4j.LoggerFactory.getLogger("Item Clumps")
        );
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
