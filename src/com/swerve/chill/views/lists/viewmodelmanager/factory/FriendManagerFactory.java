package com.swerve.chill.views.lists.viewmodelmanager.factory;

import com.swerve.chill.ParseServiceAccessor;
import com.swerve.chill.model.remote.User;
import com.swerve.chill.views.contracts.ServiceAccessor;
import com.swerve.chill.views.contracts.DefaultViewModelManager;
import com.swerve.chill.views.lists.viewmodelmanager.modelfinder.FriendFinder;
import com.swerve.chill.views.lists.viewmodelmanager.modelviewer.FriendViewer;

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
