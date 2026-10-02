// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.item_clumps.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dasik.social.api.config.GuiHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalYaclFactory(
                "item_clumps",
                "net.instantgratification.item_clumps.config.YaclScreenHelper",
                "createScreen"
        );
    }
}
