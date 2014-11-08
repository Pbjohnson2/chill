package com.chill.views.lists;

import android.content.Context;
import android.util.AttributeSet;
import com.chill.model.remote.Message;
import com.chill.views.lists.viewmodelmanager.factory.OutboxManagerFactory;

public class OutboxListView extends MainListView<Message>{

    public OutboxListView(Context context) {
        super(context);
        initializeUI();
    }

    public OutboxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeUI();
    }

    private void initializeUI () {
        initializeList(OutboxManagerFactory.getInstance().create());
        attachAdapter();
    }
}