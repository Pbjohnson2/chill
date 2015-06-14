package com.swerve.chill.views.grids.factory;

import com.swerve.chill.ParseServiceAccessor;
import com.swerve.chill.model.local.chills.ChillDefinition;
import com.swerve.chill.views.contracts.DefaultViewModelManager;
import com.swerve.chill.views.contracts.ServiceAccessor;
import com.swerve.chill.views.grids.modelfinder.ChillDefinitionFinder;
import com.swerve.chill.views.grids.modelviewer.ChillDefinitionViewer;

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
