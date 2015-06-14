package com.swerve.chill.views.contracts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.swerve.chill.model.local.ChillManager;

import java.util.Collection;

public class DefaultViewModelManager<E> implements ViewModelManager<E> {
    private final ServiceAccessor mServiceAccessor;
    private final ModelViewer <E> mModelViewer;
    private final ModelFinder <E> mModelFinder;

    public DefaultViewModelManager(final ServiceAccessor serviceAccessor,
                                   final ModelViewer<E> modelViewer,
                                   final ModelFinder<E> modelFinder) {
        mServiceAccessor = serviceAccessor;
        mModelViewer = modelViewer;
        mModelFinder = modelFinder;
    }

    @Override
    public View getModelView(final E item,
                             final View convertView,
                             final ViewGroup parent,
                             final Context context,
                             final ChillManager chillManager) {
        return mModelViewer.getModelView(item, convertView, parent, context, chillManager);
    }

    @Override
    public Collection <E> findModels() {
        return mModelFinder.findModels(mServiceAccessor);
    }
}
