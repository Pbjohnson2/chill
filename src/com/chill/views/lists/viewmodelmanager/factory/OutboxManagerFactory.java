package com.chill.views.lists.viewmodelmanager.factory;

import com.chill.ParseServiceAccessor;
import com.chill.model.remote.Message;
import com.chill.views.contracts.ServiceAccessor;
import com.chill.views.contracts.DefaultViewModelManager;
import com.chill.views.lists.viewmodelmanager.modelfinder.OutboxFinder;
import com.chill.views.lists.viewmodelmanager.modelviewer.OutboxViewer;

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
