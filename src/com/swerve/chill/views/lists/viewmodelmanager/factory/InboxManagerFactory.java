package com.swerve.chill.views.lists.viewmodelmanager.factory;

import com.swerve.chill.ParseServiceAccessor;
import com.swerve.chill.model.remote.Message;
import com.swerve.chill.views.contracts.DefaultViewModelManager;
import com.swerve.chill.views.contracts.ServiceAccessor;
import com.swerve.chill.views.lists.viewmodelmanager.modelfinder.InboxFinder;
import com.swerve.chill.views.lists.viewmodelmanager.modelviewer.InboxViewer;

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
