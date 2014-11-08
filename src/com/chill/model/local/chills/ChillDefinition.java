package com.chill.model.local.chills;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.util.Map;

@Data
public class ChillDefinition {

    public static final Map<String, ChillDefinition> DEFINITIONS = ImmutableMap.of(
            ChillDefinitionConstants.COFFEE, new ChillDefinition(ChillDefinitionConstants.COFFEE, ChillDefinitionConstants.COFFEE_LAYOUT, ChillDefinitionConstants.COFFEE_LAYOUT_COLOR),
            ChillDefinitionConstants.CIGARETTE, new ChillDefinition(ChillDefinitionConstants.CIGARETTE, ChillDefinitionConstants.CIGARETTE_LAYOUT, ChillDefinitionConstants.CIGARETTE_LAYOUT_COLOR));
    private final String id;
    private final int layout;
    private final int color;
}
