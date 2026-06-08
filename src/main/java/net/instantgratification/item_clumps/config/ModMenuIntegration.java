// Verified against: ModMenuIntegration.java (26.1.2+)
package net.instantgratification.item_clumps.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.dasik.social.api.config.GuiHelper;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiHelper.getOptionalFactory(
                "item_clumps",
                "net.instantgratification.item_clumps.config.ClothConfigScreenHelper",
                "createFactory"
        );
    }
}
