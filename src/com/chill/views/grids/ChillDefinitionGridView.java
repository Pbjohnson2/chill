package com.chill.views.grids;

import android.content.Context;
import android.util.AttributeSet;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.views.grids.factory.ChillViewModelManagerFactory;

public class ChillDefinitionGridView extends MainGridView<ChillDefinition>{

    public ChillDefinitionGridView(Context context) {
        super(context);
        initializeUI();
    }

    public ChillDefinitionGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeUI();
    }

    private void initializeUI () {
        initializeList(ChillViewModelManagerFactory.getInstance().create());
        attachAdapter();
    }
}