// Verified against: ClothConfigScreenHelper.java (26.1.2+)
package net.instantgratification.item_clumps.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreenHelper {
    public static ConfigScreenFactory<?> createFactory() {
        return ClothConfigScreenHelper::createScreen;
    }

    public static Screen createScreen(Screen parent) {
        ItemClumpsConfig config = ItemClumpsConfig.get();
        
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.item_clumps.title"));

        builder.setSavingRunnable(ItemClumpsConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        Component[] warningTooltip = new Component[]{
            Component.translatable("config.item_clumps.warning")
        };

        // --- GENERAL CATEGORY ---
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.item_clumps.category.general"));
        
        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.item_clumps.enableClumping"), config.enableClumping)
                .setDefaultValue(true)
                .setTooltip(warningTooltip)
                .setSaveConsumer(val -> config.enableClumping = val)
                .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.item_clumps.maxClumpSize"), config.maxClumpSize)
                .setDefaultValue(9999)
                .setMin(64)
                .setTooltip(warningTooltip)
                .setSaveConsumer(val -> config.maxClumpSize = val)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.item_clumps.renderLabels"), config.renderLabels)
                .setDefaultValue(true)
                .setTooltip(warningTooltip)
                .setSaveConsumer(val -> config.renderLabels = val)
                .build());

        general.addEntry(entryBuilder.startIntField(Component.translatable("config.item_clumps.mergeRadius"), config.mergeRadius)
                .setDefaultValue(1)
                .setMin(1)
                .setMax(10)
                .setTooltip(warningTooltip)
                .setSaveConsumer(val -> config.mergeRadius = val)
                .build());

        return builder.build();
    }
}
