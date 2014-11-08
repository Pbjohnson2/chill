package com.chill.views.lists;

import android.content.Context;
import android.util.AttributeSet;
import com.chill.model.remote.Message;
import com.chill.views.lists.viewmodelmanager.factory.InboxManagerFactory;

public class InboxListView extends MainListView<Message>{

    public InboxListView(Context context) {
        super(context);
        initializeUI();
    }

    public InboxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeUI();
    }

    private void initializeUI () {
        initializeList(InboxManagerFactory.getInstance().create());
        attachAdapter();
    }
}
