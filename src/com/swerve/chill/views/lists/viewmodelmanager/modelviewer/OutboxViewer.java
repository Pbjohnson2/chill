package com.swerve.chill.views.lists.viewmodelmanager.modelviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.swerve.chill.R;
import com.swerve.chill.model.local.ChillManager;
import com.swerve.chill.model.local.chills.ChillDefinition;
import com.swerve.chill.model.remote.Message;
import org.ocpsoft.prettytime.PrettyTime;

public class OutboxViewer extends InboxViewer {
    private static final PrettyTime PRETTY_TIME = new PrettyTime();
    public OutboxViewer () {

    }

    private class ViewHolder {
        LinearLayout layoutWithButtons;
        RelativeLayout layoutWithoutButtons;
        ImageView chill;
        TextView username;
        TextView usernameAlias;
        TextView location;
        TextView date;
        TextView dateCreated;
        ImageView messageStatusImage;
    }

    @Override
    public View getModelView(Message item, View convertView, ViewGroup parent, Context context, ChillManager chillManager) {

        final ViewHolder holder;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_view_outbox_item, parent, false);
            // Locate the TextViews in listview_item.xml
            holder.layoutWithButtons = (LinearLayout) convertView.findViewById(R.id.linear_layout_all);
            holder.layoutWithoutButtons = (RelativeLayout) convertView.findViewById(R.id.relative_layout_item);
            holder.chill = (ImageView) convertView.findViewById(R.id.image_chill);
            holder.username = (TextView) convertView.findViewById(R.id.textview_username);
            holder.usernameAlias = (TextView) convertView.findViewById(R.id.textview_username_alias);
            holder.location = (TextView) convertView.findViewById(R.id.textview_location);
            holder.date = (TextView) convertView.findViewById(R.id.textview_date);
            holder.dateCreated = (TextView) convertView.findViewById(R.id.textview_date_created);
            holder.messageStatusImage = (ImageView) convertView.findViewById(R.id.image_view_message_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ChillDefinition chillDefinition = chillManager.getChillDefinition(item.getChillId());
        holder.layoutWithButtons.setBackgroundColor(getColor(chillDefinition.getColor()));
        holder.chill.setImageResource(chillDefinition.getLayout());
        setText(item, holder);
        setMessageStatusImage(context, item, holder);
        return convertView;
    }

    private void setMessageStatusImage(final Context context, final Message message, final ViewHolder holder){
        switch (message.getStatus()) {
            case Message.STATUS_ACCEPTED:
                holder.messageStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.acceptwithbackgroundicon));
                break;
            case Message.STATUS_DECLINED:
                holder.messageStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.dismisswithbackgroundicon));
                break;
            default:
                holder.messageStatusImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pendingwithbackgroundicon));
                break;
        }
    }

    private int getColor(final int color) {
        return (0xff000000 + Integer.parseInt(Integer.toHexString(color), 16));
    }

    private void setText(final Message message, final ViewHolder holder) {
        holder.username.setText(message.getTo().getUsername());
        holder.usernameAlias.setText(message.getTo().getUsername().substring(0, 1));
        holder.location.setText(message.getLocation());
        holder.date.setText(message.getTime());
        holder.dateCreated.setText(PRETTY_TIME.format(message.getCreatedAt()));
    }

    private int getColor(final ChillDefinition chillDefinition) {
        return (0xff000000 + Integer.parseInt(Integer.toHexString(chillDefinition.getColor()), 16));
    }
}
