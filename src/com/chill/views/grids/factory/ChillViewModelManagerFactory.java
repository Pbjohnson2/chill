package com.chill.views.grids.factory;

import com.chill.ParseServiceAccessor;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.views.contracts.DefaultViewModelManager;
import com.chill.views.contracts.ServiceAccessor;
import com.chill.views.grids.modelfinder.ChillDefinitionFinder;
import com.chill.views.grids.modelviewer.ChillDefinitionViewer;

public class ChillViewModelManagerFactory {

    private final ServiceAccessor mServiceAccessor;

    private ChillViewModelManagerFactory(final ServiceAccessor serviceAccessor) {
        mServiceAccessor = serviceAccessor;
    }

    public static ChillViewModelManagerFactory getInstance() {
        return new ChillViewModelManagerFactory(new ParseServiceAccessor());
    }

    public DefaultViewModelManager <ChillDefinition> create() {
        return new DefaultViewModelManager<ChillDefinition>(
                mServiceAccessor,
                new ChillDefinitionViewer(),
                new ChillDefinitionFinder());
    }
}
