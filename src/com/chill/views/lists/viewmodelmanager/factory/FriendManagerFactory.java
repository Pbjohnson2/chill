package com.chill.views.lists.viewmodelmanager.factory;

import com.chill.ParseServiceAccessor;
import com.chill.model.remote.User;
import com.chill.views.contracts.ServiceAccessor;
import com.chill.views.contracts.DefaultViewModelManager;
import com.chill.views.lists.viewmodelmanager.modelfinder.FriendFinder;
import com.chill.views.lists.viewmodelmanager.modelviewer.FriendViewer;

public class FriendManagerFactory {

    private final ServiceAccessor mServiceAccessor;

    private FriendManagerFactory(final ServiceAccessor serviceAccessor) {
        mServiceAccessor = serviceAccessor;
    }

    public static FriendManagerFactory getInstance() {
        return new FriendManagerFactory(new ParseServiceAccessor());
    }

    public DefaultViewModelManager<User> create() {
        return new DefaultViewModelManager<User>(
                mServiceAccessor,
                new FriendViewer(),
                new FriendFinder());
    }
}
