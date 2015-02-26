package com.chill.views.lists.viewmodelmanager.modelviewer;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.chill.ParseServiceAccessor;
import com.chill.R;
import com.chill.model.local.ChillManager;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.model.remote.Message;
import com.chill.views.contracts.ModelViewer;
import com.chill.views.contracts.ServiceAccessor;
import com.google.common.collect.ImmutableList;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

public class InboxViewer implements ModelViewer<Message>{
    private static final PrettyTime PRETTY_TIME = new PrettyTime();

    private class ViewHolder {
        LinearLayout layoutWithButtons;
        RelativeLayout layoutWithoutButtons;
        View styleBar;
        ImageView chill;
        TextView username;
        TextView usernameAlias;
        TextView location;
        TextView date;
        TextView dateCreated;
        ImageView acceptImageView;
        ImageView dismissImageView;
        ImageView acceptBackgroundImageView;
        ImageView dismissBackgroundImageView;
        Button acceptButton;
        Button dismissButton;
    }

    @Override
    public View getModelView(Message item, View convertView, ViewGroup parent, Context context, ChillManager chillManager) {
        final ViewHolder holder;
        View view;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            holder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.list_view_inbox_item, parent, false);

            holder.layoutWithButtons = (LinearLayout) view.findViewById(R.id.linear_layout_all);
            holder.layoutWithoutButtons = (RelativeLayout) view.findViewById(R.id.relative_layout_item);
            holder.styleBar = view.findViewById(R.id.style_bar);
            holder.chill = (ImageView) view.findViewById(R.id.image_chill);
            holder.username = (TextView) view.findViewById(R.id.textview_username);
            holder.usernameAlias = (TextView) view.findViewById(R.id.textview_username_alias);
            holder.location = (TextView) view.findViewById(R.id.textview_location);
            holder.date = (TextView) view.findViewById(R.id.textview_date);
            holder.dateCreated = (TextView) view.findViewById(R.id.textview_date_created);
            holder.acceptImageView = (ImageView) view.findViewById(R.id.image_view_accept);
            holder.dismissImageView = (ImageView) view.findViewById(R.id.image_view_dismiss);
            holder.acceptBackgroundImageView = (ImageView) view.findViewById(R.id.image_view_accept_background);
            holder.dismissBackgroundImageView = (ImageView) view.findViewById(R.id.image_view_dismiss_background);
            holder.acceptButton = (Button) view.findViewById(R.id.button_accept);
            holder.dismissButton = (Button) view.findViewById(R.id.button_dismiss);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }
        ChillDefinition chillDefinition = chillManager.getChillDefinition(item.getChillId());
        holder.layoutWithButtons.setBackgroundColor(getColor(chillDefinition.getColor()));
        holder.chill.setImageResource(chillDefinition.getLayout());
        setButtonImages(context, item, holder, chillDefinition);
        setButtons(item, holder, chillDefinition);
        setText(item, holder);

        return view;
    }

    private void setButtonImages(final Context context, final Message message, final ViewHolder holder, final ChillDefinition chillDefinition){
        switch (message.getStatus()) {
            case Message.STATUS_ACCEPTED:
                holder.acceptButton.setClickable(false);
                holder.dismissButton.setClickable(true);
                holder.acceptImageView.setImageResource(R.drawable.acceptwithbackgroundicon);
                holder.dismissImageView.setImageResource(R.drawable.dismisswithoutbackgroundicon);
                break;
            case Message.STATUS_DECLINED:
                holder.dismissButton.setClickable(false);
                holder.acceptButton.setClickable(true);
                holder.acceptImageView.setImageResource(R.drawable.acceptwithoutbackground);
                holder.dismissImageView.setImageResource(R.drawable.dismisswithbackgroundicon);
                break;
        }
    }

    private void setButtons(final Message message, final ViewHolder holder, final ChillDefinition chillDefinition){
        final ServiceAccessor serviceAccessor = new ParseServiceAccessor();

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickable(holder.dismissButton, holder.acceptButton);
                setMessage(serviceAccessor, message, Message.STATUS_ACCEPTED);
                holder.acceptImageView.setImageResource(R.drawable.acceptwithbackgroundicon);
                holder.dismissImageView.setImageResource(R.drawable.dismisswithoutbackgroundicon);
            }
        });

        holder.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickable(holder.acceptButton, holder.dismissButton);
                setMessage(serviceAccessor, message, Message.STATUS_DECLINED);
                holder.dismissImageView.setImageResource(R.drawable.dismisswithbackgroundicon);
                holder.acceptImageView.setImageResource(R.drawable.acceptwithoutbackground);
            }
        });
    }

    private void setText(final Message message, final ViewHolder holder) {
        holder.username.setText(message.getFrom().getUsername());
        holder.location.setText(message.getLocation());
        holder.usernameAlias.setText(message.getFrom().getUsername().substring(0, 1));
        holder.date.setText(message.getTime());
        holder.dateCreated.setText(PRETTY_TIME.format(message.getCreatedAt()));
    }

    private int getColor(final ChillDefinition chillDefinition) {
        return (0xff000000 + Integer.parseInt(Integer.toHexString(chillDefinition.getColor()), 16));
    }

    private int getColor(final int color) {
        return (0xff000000 + Integer.parseInt(Integer.toHexString(color), 16));
    }

    private ObjectAnimator transitionColor(final View view, final int colorFrom, final int colorTo){
        final ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", colorFrom, colorTo);
        animator.setDuration(300);
        animator.setEvaluator(new ArgbEvaluator());
        return animator;
    }

    private void animateChangeButtonColor(final View view, final int colorFrom, final int colorTo) {
        AnimatorSet animationSet = new AnimatorSet();
        List<ObjectAnimator> animationList = ImmutableList.of(
                transitionColor(view, colorFrom, colorTo));
        for (int i = 0; i < animationList.size() - 1; i ++) {
            animationSet.play(animationList.get(i)).before(animationList.get(i + 1));
        }
        animationSet.start();
    }

    private void setClickable(final Button clickable, final Button notClickable) {
        clickable.setClickable(true);
        notClickable.setClickable(false);
    }

    private void setMessage(final ServiceAccessor serviceAccessor, final Message message, final String messageStatus) {
        new Thread() {
            @Override
            public void run() {
                serviceAccessor.setMessageStatus(message, messageStatus);
            }
        }.start();
    }
}
