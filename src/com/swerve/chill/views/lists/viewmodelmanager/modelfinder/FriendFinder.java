package com.swerve.chill.views.lists.viewmodelmanager.modelfinder;

import com.swerve.chill.model.remote.User;
import com.swerve.chill.views.contracts.ModelFinder;
import com.swerve.chill.views.contracts.ServiceAccessor;

import java.util.Collection;

public class FriendFinder implements ModelFinder <User> {
    public FriendFinder () {

    }

    @Override
    public Collection<User> findModels(ServiceAccessor serviceAccessor) {
        return serviceAccessor.getFriends();
    }
}