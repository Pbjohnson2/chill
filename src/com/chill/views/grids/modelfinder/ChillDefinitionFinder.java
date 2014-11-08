package com.chill.views.grids.modelfinder;

import com.chill.model.local.chills.ChillDefinition;
import com.chill.views.contracts.ModelFinder;
import com.chill.views.contracts.ServiceAccessor;

import java.util.Collection;

public class ChillDefinitionFinder implements ModelFinder<ChillDefinition> {
    public ChillDefinitionFinder() {

    }

    @Override
    public Collection<ChillDefinition> findModels(ServiceAccessor serviceAccessor) {
        return ChillDefinition.DEFINITIONS.values();
    }
}