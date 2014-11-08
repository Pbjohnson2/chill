package com.chill.views.lists.viewmodelmanager.modelviewer;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.*;
import com.chill.ParseServiceAccessor;
import com.chill.R;
import com.chill.model.local.ChillManager;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.model.local.chills.ChillDefinitionConstants;
import com.chill.model.remote.Message;
import com.chill.views.contracts.ModelViewer;
import com.chill.views.contracts.ServiceAccessor;
import com.google.common.collect.ImmutableList;

import java.util.List;

public class InboxViewer implements ModelViewer<Message>{

    private class ViewHolder {
        LinearLayout layoutWithButtons;
        RelativeLayout layoutWithoutButtons;
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
            holder.layoutWithButtons = (LinearLayout) convertView.findViewById(R.id.linear_layout_all);
            holder.layoutWithoutButtons = (RelativeLayout) convertView.findViewById(R.id.relative_layout_item);
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
        setBackgroundColors(item, holder, chillDefinition);
        setButtons(item, holder, chillDefinition);
        setText(item, holder);

        return convertView;
    }

    private void setBackgroundColors(final Message message, final ViewHolder holder, final ChillDefinition chillDefinition){
        switch (message.getStatus()) {
            case Message.STATUS_ACCEPTED:
                holder.layoutWithButtons.setBackgroundColor(getColor(ChillDefinitionConstants.ACCEPT_CHILL));
                break;
            case Message.STATUS_DECLINED:
                holder.layoutWithButtons.setBackgroundColor(getColor(ChillDefinitionConstants.DISMISS_CHILL));
                break;
            case Message.STATUS_PENDING:
                holder.layoutWithButtons.setBackgroundColor(getColor(chillDefinition));
                break;
        }
    }

    private void setButtons(final Message message, final ViewHolder holder, final ChillDefinition chillDefinition){

        if (!message.getStatus().equals(Message.STATUS_PENDING)) {
            hideButtons(holder);
            return;
        }

        final ServiceAccessor serviceAccessor = new ParseServiceAccessor();

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonRemoval(holder, getColor(chillDefinition), getColor(ChillDefinitionConstants.ACCEPT_CHILL));
                serviceAccessor.setMessageStatus(message, Message.STATUS_ACCEPTED);
            }
        });

        holder.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonRemoval(holder, getColor(chillDefinition), getColor(ChillDefinitionConstants.DISMISS_CHILL));
                serviceAccessor.setMessageStatus(message, Message.STATUS_DECLINED);
            }
        });
    }

    private void hideButtons(final ViewHolder holder) {
        holder.acceptButton.setVisibility(View.GONE);
        holder.dismissButton.setVisibility(View.GONE);
        holder.styleBar.setVisibility(View.GONE);
    }

    private void setText(final Message message, final ViewHolder holder) {
        holder.username.setText(message.getFrom().getUsername());
        holder.location.setText(message.getLocation());
        holder.date.setText(message.getTime());
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

    private void animateButtonRemoval(final ViewHolder holder, final int colorFrom, final int colorTo) {
        AnimatorSet animationSet = new AnimatorSet();
        List<ObjectAnimator> animationList = ImmutableList.of(
                transitionColor(holder.layoutWithButtons, colorFrom, colorTo),
                fadeView(holder.styleBar),
                fadeView(holder.acceptButton),
                fadeView(holder.dismissButton),
                collapseLayout(holder));
        for (int i = 0; i < animationList.size() - 1; i ++) {
            animationSet.play(animationList.get(i)).before(animationList.get(i + 1));
        }
        animationSet.start();
    }

    private ObjectAnimator fadeView(final View view) {
        final AccelerateInterpolator interpolator = new AccelerateInterpolator(2);
        final ObjectAnimator fadeOut = ObjectAnimator.ofFloat(view, "alpha",
                0f);
        fadeOut.setDuration(300);
        fadeOut.setInterpolator(interpolator);
        return fadeOut;
    }

    private ObjectAnimator collapseLayout(final ViewHolder holder){
        final ObjectAnimator animator = ObjectAnimator.ofFloat(holder.layoutWithButtons, "bottom", holder.layoutWithButtons.getHeight());
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(1000);
        return animator;
    }
/*
    private Animation fadeView(final View view) {

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(800);
        fadeOut.setRepeatCount(0);
        view.setAnimation(fadeOut);
        view.setVisibility(View.INVISIBLE);
        return fadeOut;
    }*/


}
