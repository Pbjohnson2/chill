package com.chill.main.message;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.chill.ParseServiceAccessor;
import com.chill.R;
import com.chill.edit.EditChillActivity;
import com.chill.main.contracts.NavigationListener;
import com.chill.model.local.ChillManager;
import com.chill.model.local.ChillPreference;
import com.chill.model.local.chills.ChillDefinition;
import com.chill.model.local.chills.ChillDefinitionConstants;
import com.chill.model.remote.User;
import com.chill.util.TextValidator;
import com.chill.views.lists.FriendListView;
import com.chill.views.contracts.ServiceAccessor;
import com.faizmalkani.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.ImmutableList;
import lombok.Data;

public class MessageFragment extends Fragment {
    private RelativeLayout mEditChillLayout;
    private TextView mLocation;
    private TextView mDate;
    private ListView mListView;
    private ImageView mHeaderChillImage;

    private FriendListView mFriendListView;
    private FloatingActionButton mNavigateOutboxButton;
    private FloatingActionButton mNavigateInboxButton;
    private ImageButton mEditChillButton;

    private LinearLayout mAddFriendLayout;
    private EditText mAddFriendEditText;


    private ServiceAccessor mServiceAccessor;
    private TextValidator mTextValidator;

    private NavigationListener mNavigationListener;
    private ChillManager mChillManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        mFriendListView = (FriendListView) rootView.findViewById(R.id.friend_list_view);
        mListView = (ListView) mFriendListView.findViewById(R.id.list_view_main);

        final ViewGroup header = (ViewGroup) inflater.inflate(R.layout.list_view_message_header, mListView,
                false);
        final ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.list_view_message_footer, mListView,
                false);
        mListView.addHeaderView(header, null, false);
        mListView.addFooterView(footer, null, false);
        mHeaderChillImage = (ImageView) header.findViewById(R.id.image_view_chill);
        mFriendListView.attachAdapter();
        mEditChillLayout = (RelativeLayout) rootView.findViewById(R.id.edit_chill);
        mLocation = (TextView) rootView.findViewById(R.id.textview_location);
        mDate = (TextView) rootView.findViewById(R.id.textview_date);
        mNavigateOutboxButton = (FloatingActionButton) rootView.findViewById(R.id.button_navigate_outbox);
        mNavigateInboxButton = (FloatingActionButton) rootView.findViewById(R.id.button_navigate_inbox);

        mEditChillButton = (ImageButton) rootView.findViewById(R.id.button_edit_chill);

        mAddFriendLayout = (LinearLayout) rootView.findViewById(R.id.add_friend_layout);
        mAddFriendEditText = (EditText) rootView.findViewById(R.id.add_friend);

        mServiceAccessor = new ParseServiceAccessor();
        mTextValidator = new TextValidator();
        mChillManager = new ChillManager(getActivity());

        initializeChillPreference();
        initializeUI();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeChillPreference();
        initializeUI();
    }

    private void initializeChillPreference() {
        final ChillPreference chillPreference = mChillManager.getChillPreference();
        mLocation.setText(chillPreference.getLocation());
        mDate.setText(chillPreference.getDate());
        ChillDefinition definition = mChillManager.getChillDefinition(chillPreference.getChill().getId());
        mHeaderChillImage.setImageResource(definition.getLayout());
        setColors(0xff000000 + Integer.parseInt(Integer.toHexString(definition.getColor()), 16));
    }

    private void setColors(final int color) {
        mEditChillLayout.setBackgroundColor(color);
        mAddFriendLayout.setBackgroundColor(color);
        mListView.getDivider().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    private void initializeUI(){
        mAddFriendEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mAddFriendEditText.setHint("");
            }
        });
        mNavigateOutboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationListener.onClickOutbox();
            }
        });
        mNavigateInboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationListener.onClickInbox();
            }
        });
        mEditChillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), EditChillActivity.class);
                startActivity(intent);
            }
        });
        mAddFriendEditText.setImeActionLabel("Add Friend", KeyEvent.KEYCODE_ENTER);
        mAddFriendEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new AddFriendTask().execute();
                    return true;
                }
                return false;
            }
        });
        mAddFriendEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int actionId,
                                          KeyEvent arg2) {
                // hide the keyboard and search the web when the enter key
                // button is pressed
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    new AddFriendTask().execute();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mNavigationListener = (NavigationListener) activity;
    }

    @Data
    private class ServiceAccessorResult{
        private final boolean success;
        private final String message;
        private final User friend;
    }

    private class AddFriendTask extends AsyncTask<Void, Void, ServiceAccessorResult> {
        @Override
        protected ServiceAccessorResult doInBackground(Void... params) {
            if (!mTextValidator.isValidEditText(mAddFriendEditText)) {
                return new ServiceAccessorResult(false, "Please enter a username and password", null);
            }

            final String username = mAddFriendEditText.getText().toString().toUpperCase();
            final User friend = mServiceAccessor.addFriend(username);

            if (friend != null) {
                return new ServiceAccessorResult(true, "", friend);
            }
            return new ServiceAccessorResult(false, "Username or password was incorrect.", null);
        }

        @Override
        protected void onPostExecute(final ServiceAccessorResult result) {
            super.onPostExecute(result);
            if (result.isSuccess()) {
                mAddFriendEditText.clearFocus();
                mAddFriendEditText.setText("");
                mAddFriendEditText.setHint("+");
                mFriendListView.addItem(ImmutableList.of(result.getFriend()));
                mFriendListView.notifyDataSetChanged();
            } else {
                toast("Could not add friend.");
            }
        }
    }

    private void toast (final String message){
        final Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}