package com.chill.model.local.chills;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.util.Map;

@Data
public class ChillDefinition {
    public static final Map<String, ChillDefinition> DEFINITIONS = ImmutableMap.<String, ChillDefinition>builder()
            .put(ChillDefinitionConstants.COFFEE, new ChillDefinition(ChillDefinitionConstants.COFFEE, ChillDefinitionConstants.COFFEE_LAYOUT, ChillDefinitionConstants.COFFEE_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.FOOD, new ChillDefinition(ChillDefinitionConstants.FOOD, ChillDefinitionConstants.FOOD_LAYOUT, ChillDefinitionConstants.FOOD_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.GAMING, new ChillDefinition(ChillDefinitionConstants.GAMING, ChillDefinitionConstants.GAMING_LAYOUT, ChillDefinitionConstants.GAMING_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.CODING, new ChillDefinition(ChillDefinitionConstants.CODING, ChillDefinitionConstants.CODING_LAYOUT, ChillDefinitionConstants.CODING_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.LIFTING, new ChillDefinition(ChillDefinitionConstants.LIFTING, ChillDefinitionConstants.LIFTING_LAYOUT, ChillDefinitionConstants.LIFTING_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.SEX, new ChillDefinition(ChillDefinitionConstants.SEX, ChillDefinitionConstants.SEX_LAYOUT, ChillDefinitionConstants.SEX_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.WINE, new ChillDefinition(ChillDefinitionConstants.WINE, ChillDefinitionConstants.WINE_LAYOUT, ChillDefinitionConstants.WINE_LAYOUT_COLOR))
            .put(ChillDefinitionConstants.CIGARETTE, new ChillDefinition(ChillDefinitionConstants.CIGARETTE, ChillDefinitionConstants.CIGARETTE_LAYOUT, ChillDefinitionConstants.CIGARETTE_LAYOUT_COLOR))
            .build();
    private final String id;
    private final int layout;
    private final int color;
}
