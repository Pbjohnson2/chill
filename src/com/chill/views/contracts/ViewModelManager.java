package com.chill.views.contracts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.chill.model.local.ChillManager;

import java.util.Collection;
import java.util.List;

public interface ViewModelManager<E>{
    View getModelView (final E item, final View convertView, final ViewGroup parent, final Context context, final ChillManager chillManager);
    Collection <E> findModels();
}
