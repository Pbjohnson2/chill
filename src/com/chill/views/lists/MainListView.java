package com.chill.views.lists;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.chill.R;
import com.chill.views.DynamicContentView;
import com.chill.views.ItemAdapter;
import com.chill.views.ItemScrollListener;
import com.chill.views.contracts.ViewModelManager;

import java.util.ArrayList;
import java.util.List;

public class MainListView<E> extends DynamicContentView<E> {
    private ListView mMainListView;

    public MainListView(Context context) {
        super(context);
        mMainListView = (ListView) findViewById(R.id.list_view_main);
    }

    public MainListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMainListView = (ListView) findViewById(R.id.list_view_main);
    }

    @Override
    public void attachOnScrollListener() {
        mMainListView.setOnScrollListener(mItemScrollListener);
    }

    @Override
    public void attachAdapter(){
        mMainListView.setAdapter(mItemAdapter);
    }

    @Override
    public void inflateView() {
        inflate(getContext(), R.layout.list_view_main, this);
    }

    public void setDivider(final Drawable drawable){
        mMainListView.setDivider(drawable);
    }
}
