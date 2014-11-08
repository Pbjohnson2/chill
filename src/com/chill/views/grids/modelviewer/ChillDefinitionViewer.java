package com.chill.views.grids.modelviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.chill.R;
import com.chill.edit.EditChillActivity;
import com.chill.model.local.ChillManager;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.views.contracts.ModelViewer;

public class ChillDefinitionViewer implements ModelViewer<ChillDefinition> {
    public ChillDefinitionViewer() {

    }

    private class ViewHolder {
        LinearLayout layoutWithImageButton;
        ImageButton chillImageButton;
    }

    @Override
    public View getModelView(final ChillDefinition item, View convertView, ViewGroup parent, final Context context, final ChillManager chillManager) {
        final ViewHolder holder;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.grid_view_chill_item, parent, false);
            // Locate the TextViews in listview_item.xml
            holder.layoutWithImageButton = (LinearLayout) convertView.findViewById(R.id.layout_with_chill_button);
            holder.chillImageButton = (ImageButton) convertView.findViewById(R.id.image_button_chill);

            setLayoutColor(holder.layoutWithImageButton, item);
            holder.chillImageButton.setBackground(context.getResources().getDrawable(item.getLayout()));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.chillImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chillManager.updateChillPreference(item);
                final Intent intent = new Intent(context, EditChillActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        return convertView;
    }

    private void setLayoutColor(final LinearLayout linearLayout, final ChillDefinition definition) {
        linearLayout.setBackgroundColor(0xff000000 + Integer.parseInt(Integer.toHexString(definition.getColor()), 16));
    }
}