package com.chill.views.lists.viewmodelmanager.factory;

import com.chill.ParseServiceAccessor;
import com.chill.model.remote.Message;
import com.chill.views.contracts.DefaultViewModelManager;
import com.chill.views.contracts.ServiceAccessor;
import com.chill.views.lists.viewmodelmanager.modelfinder.InboxFinder;
import com.chill.views.lists.viewmodelmanager.modelviewer.InboxViewer;

public class InboxManagerFactory {

    private final ServiceAccessor mServiceAccessor;

    private InboxManagerFactory(final ServiceAccessor serviceAccessor) {
        mServiceAccessor = serviceAccessor;
    }

    public static InboxManagerFactory getInstance() {
        return new InboxManagerFactory(new ParseServiceAccessor());
    }

    public DefaultViewModelManager<Message> create() {
        return new DefaultViewModelManager<Message>(
                mServiceAccessor,
                new InboxViewer(),
                new InboxFinder());
    }
}
