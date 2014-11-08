package com.chill.views.grids;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import com.chill.R;
import com.chill.views.DynamicContentView;


public class MainGridView<E> extends DynamicContentView<E> {
    private GridView mMainGridView;

    public MainGridView(Context context) {
        super(context);
        mMainGridView = (GridView) findViewById(R.id.grid_view_main);
    }

    public MainGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMainGridView = (GridView) findViewById(R.id.grid_view_main);
    }

    @Override
    public void attachOnScrollListener() {
        mMainGridView.setOnScrollListener(mItemScrollListener);
    }

    @Override
    public void attachAdapter(){
        mMainGridView.setAdapter(mItemAdapter);
    }

    @Override
    public void inflateView() {
        inflate(getContext(), R.layout.grid_view_main, this);
    }
}
