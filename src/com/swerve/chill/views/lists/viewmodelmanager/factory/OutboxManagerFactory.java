package com.swerve.chill.views.lists.viewmodelmanager.factory;

import com.swerve.chill.ParseServiceAccessor;
import com.swerve.chill.model.remote.Message;
import com.swerve.chill.views.contracts.ServiceAccessor;
import com.swerve.chill.views.contracts.DefaultViewModelManager;
import com.swerve.chill.views.lists.viewmodelmanager.modelfinder.OutboxFinder;
import com.swerve.chill.views.lists.viewmodelmanager.modelviewer.OutboxViewer;

public class OutboxManagerFactory {

    private final ServiceAccessor mServiceAccessor;

    private OutboxManagerFactory(final ServiceAccessor serviceAccessor) {
        mServiceAccessor = serviceAccessor;
    }

    public static OutboxManagerFactory getInstance() {
        return new OutboxManagerFactory(new ParseServiceAccessor());
    }

    public DefaultViewModelManager<Message> create() {
        return new DefaultViewModelManager<Message>(
                mServiceAccessor,
                new OutboxViewer(),
                new OutboxFinder());
    }
}
