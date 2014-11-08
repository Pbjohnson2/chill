package com.chill.views.lists.viewmodelmanager.modelviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.chill.R;
import com.chill.model.local.ChillManager;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.model.local.chills.ChillDefinitionConstants;
import com.chill.model.remote.Message;

public class OutboxViewer extends InboxViewer {
    public OutboxViewer () {

    }

    private class ViewHolder {
        RelativeLayout outbox_item;
        View styleBar;
        ImageView chill;
        TextView username;
        TextView location;
        TextView date;
        Button acceptButton;
        Button dismissButton;
    }

    @Override
    public View getModelView(Message item, View convertView, ViewGroup parent, Context context, ChillManager chillManager) {

        final ViewHolder holder;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_view_message_item, parent, false);
            // Locate the TextViews in listview_item.xml
            holder.outbox_item = (RelativeLayout) convertView.findViewById(R.id.relative_layout_item);
            holder.styleBar = convertView.findViewById(R.id.style_bar);
            holder.chill = (ImageView) convertView.findViewById(R.id.image_chill);
            holder.username = (TextView) convertView.findViewById(R.id.textview_username);
            holder.location = (TextView) convertView.findViewById(R.id.textview_location);
            holder.date = (TextView) convertView.findViewById(R.id.textview_date);
            holder.acceptButton = (Button) convertView.findViewById(R.id.button_accept);
            holder.dismissButton = (Button) convertView.findViewById(R.id.button_dismiss);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ChillDefinition chillDefinition = chillManager.getChillDefinition(item.getChillId());
        holder.chill.setBackground(context.getResources().getDrawable(chillDefinition.getLayout()));
        setText(item, holder);
        setBackgroundColors(item, holder, chillDefinition);
        hideButtons(holder);
        return convertView;
    }

    private void setBackgroundColors(final Message message, final ViewHolder holder, final ChillDefinition chillDefinition){
        switch (message.getStatus()) {
            case Message.STATUS_ACCEPTED:
                holder.outbox_item.setBackgroundColor(getColor(ChillDefinitionConstants.ACCEPT_CHILL));
                break;
            case Message.STATUS_DECLINED:
                holder.outbox_item.setBackgroundColor(getColor(ChillDefinitionConstants.DISMISS_CHILL));
                break;
            case Message.STATUS_PENDING:
                holder.outbox_item.setBackgroundColor(getColor(chillDefinition));
                break;
        }
    }

    private int getColor(final int color) {
        return (0xff000000 + Integer.parseInt(Integer.toHexString(color), 16));
    }

    private void setText(final Message message, final ViewHolder holder) {
        holder.username.setText(message.getTo().getUsername());
        holder.location.setText(message.getLocation());
        holder.date.setText(message.getTime());
    }

    private int getColor(final ChillDefinition chillDefinition) {
        return (0xff000000 + Integer.parseInt(Integer.toHexString(chillDefinition.getColor()), 16));
    }

    private void hideButtons(final ViewHolder holder) {
        holder.acceptButton.setVisibility(View.GONE);
        holder.dismissButton.setVisibility(View.GONE);
        holder.styleBar.setVisibility(View.GONE);
    }
}
