package com.chill.views.lists.viewmodelmanager.modelfinder;

import com.chill.model.remote.User;
import com.chill.views.contracts.ModelFinder;
import com.chill.views.contracts.ServiceAccessor;

import java.util.Collection;

public class FriendFinder implements ModelFinder <User> {
    public FriendFinder () {

    }

    @Override
    public Collection<User> findModels(ServiceAccessor serviceAccessor) {
        return serviceAccessor.getFriends();
    }
}