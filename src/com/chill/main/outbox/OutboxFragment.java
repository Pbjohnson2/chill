package com.chill.main.outbox;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.chill.ChillConstants;
import com.chill.R;
import com.chill.main.contracts.NavigationListener;
import com.chill.views.lists.OutboxListView;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

public class OutboxFragment extends Fragment {
    private OutboxListView mOutboxListView;
    private FloatingActionButton mNavigateMessageButton;

    private NavigationListener mNavigationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_outbox, container, false);
        mOutboxListView = (OutboxListView) rootView.findViewById(R.id.outbox_list_view);
        mNavigateMessageButton = (FloatingActionButton) rootView.findViewById(R.id.button_navigate_message);

        initializeUI();
        return rootView;
    }

    private void initializeUI(){
        mOutboxListView.setDivider(getResources().getDrawable(R.drawable.list_view_divider_white));
        mNavigateMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationListener.onClickMessage();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mNavigationListener = (NavigationListener) activity;
    }
}