package com.chill.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.chill.model.local.ChillManager;
import com.chill.views.contracts.ViewModelManager;

import java.util.Collection;
import java.util.List;

public class ItemAdapter<E> extends BaseAdapter {
    private final ViewModelManager<E> mViewModelManager;
    private List<E> mItems;
    private Context mContext;
    private ChillManager mChillManager;

    public ItemAdapter(final Context context,
                       final List<E> items,
                       final ViewModelManager<E> viewModelManager) {
        super();
        mItems = items;
        mContext = context;
        mViewModelManager = viewModelManager;
        mChillManager = new ChillManager(context);
    }

    @Override
    public int getCount() {
        return mItems.size() ;
    }

    @Override
    public E getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).hashCode();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
       return mViewModelManager.getModelView(mItems.get(position), convertView, parent, mContext, mChillManager);
    }

    public void addAll(Collection<E> collection){
        mItems.addAll(collection);
    }

    public void clear(){
        mItems.clear();
    }
}
