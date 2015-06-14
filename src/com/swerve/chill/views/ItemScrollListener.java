package com.swerve.chill.views;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import com.swerve.chill.views.contracts.ViewModelManager;

import java.util.Collection;

public class ItemScrollListener<E> implements AbsListView.OnScrollListener {
    private final ItemAdapter<E> mAdapter;
    private final ViewModelManager<E> mViewModelManager;
    private final SwipeRefreshLayout mSwipeRefreshLayout;


    public ItemScrollListener(final ItemAdapter<E> adapter,
                              final ViewModelManager<E> viewModelManager,
                              final SwipeRefreshLayout swipeRefreshLayout) {
        this.mAdapter = adapter;
        this.mViewModelManager = viewModelManager;
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        new FindDataTask().execute();
    }

    public void refreshData(){
        new FindDataTask().execute();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    private class FindDataTask extends AsyncTask<Void, E, Collection<E>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clearData();
        }
        @Override
        protected Collection<E> doInBackground(Void... params) {
            return mViewModelManager.findModels();
        }

        @Override
        protected void onPostExecute(Collection<E> items) {
            super.onPostExecute(items);
            mAdapter.addAll(items);
            mAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void clearData() {
        mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }
}
