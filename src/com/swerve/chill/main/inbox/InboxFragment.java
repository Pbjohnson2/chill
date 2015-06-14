package com.swerve.chill.main.inbox;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.swerve.chill.R;
import com.swerve.chill.main.contracts.NavigationListener;
import com.swerve.chill.views.lists.InboxListView;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;

public class InboxFragment extends Fragment {
    private InboxListView mInboxListView;
    private ListView mListView;
    private FloatingActionButton mNavigateMessageButton;

    private NavigationListener mNavigationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        mInboxListView = (InboxListView) rootView.findViewById(R.id.inbox_list_view);
        mListView = (ListView) mInboxListView.findViewById(R.id.list_view_main);
        mNavigateMessageButton = (FloatingActionButton) rootView.findViewById(R.id.button_navigate_message);

        initializeUI();
        return rootView;
    }

    private void initializeUI(){
        mNavigateMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationListener.onClickMessage();
            }
        });
        mListView.getDivider().setColorFilter(0xff000000 + Integer.parseInt(Integer.toHexString(0xFFFFFF), 16), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mNavigationListener = (NavigationListener) activity;
    }
}