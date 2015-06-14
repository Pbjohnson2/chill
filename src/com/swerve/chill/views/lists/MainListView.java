package com.swerve.chill.views.lists;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ListView;
import com.swerve.chill.R;
import com.swerve.chill.views.DynamicContentView;

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
