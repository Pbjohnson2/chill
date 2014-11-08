package com.chill.edit;

import android.app.Activity;
import android.os.Bundle;
import com.chill.R;
import com.chill.views.grids.ChillDefinitionGridView;

public class SelectChillActivity extends Activity {
    private ChillDefinitionGridView mChillDefinitionGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chill);

        mChillDefinitionGridView = (ChillDefinitionGridView) findViewById(R.id.grid_view_chill_definition);
    }
}