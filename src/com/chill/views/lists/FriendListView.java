package com.chill.views.lists;

import android.content.Context;
import android.util.AttributeSet;
import com.chill.model.remote.User;
import com.chill.views.lists.viewmodelmanager.factory.FriendManagerFactory;

public class FriendListView extends MainListView<User>{
    public FriendListView(Context context) {
        super(context);
        initializeList(FriendManagerFactory.getInstance().create());
    }

    public FriendListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeList(FriendManagerFactory.getInstance().create());
    }
}
