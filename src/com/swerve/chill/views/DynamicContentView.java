package com.swerve.chill.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.swerve.chill.R;
import com.swerve.chill.views.contracts.ViewModelManager;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicContentView <E> extends RelativeLayout {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public ItemAdapter<E> mItemAdapter;
    public ItemScrollListener<E> mItemScrollListener;

    public DynamicContentView(Context context) {
        super(context);
        initializeUI();
    }

    public DynamicContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeUI();
    }

    private void initializeUI(){
        inflateView();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
    }

    public void initializeList(final ViewModelManager<E> viewModelManager) {
        mItemAdapter = new ItemAdapter<E>(
                getContext(),
                new ArrayList<E>(),
                viewModelManager);
        mItemScrollListener = new ItemScrollListener<E>(
                mItemAdapter,
                viewModelManager,
                mSwipeRefreshLayout);
        attachOnScrollListener();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mItemScrollListener.refreshData();
            }
        });
    }

    public void addItem (final List<E> items) {
        mItemAdapter.addAll(items);
    }
    public void notifyDataSetChanged () {
        mItemAdapter.notifyDataSetChanged();
    }

    public abstract void attachOnScrollListener();
    public abstract void attachAdapter();
    public abstract void inflateView();
}
