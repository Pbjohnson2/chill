package com.swerve.chill.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.swerve.chill.main.inbox.InboxFragment;
import com.swerve.chill.main.message.MessageFragment;
import com.swerve.chill.main.outbox.OutboxFragment;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    public static final int OUTBOX_FRAGMENT = 0;
    public static final int MESSAGE_FRAGMENT = 1;
    public static final int INBOX_FRAGMENT = 2;

    private OutboxFragment mOutboxFragment;
    private MessageFragment mMessageFragment;
    private InboxFragment mInboxFragment;

    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case OUTBOX_FRAGMENT:
                if(mOutboxFragment == null) {
                    mOutboxFragment = new OutboxFragment();
                }
                return mOutboxFragment;
            case MESSAGE_FRAGMENT:
                if(mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                }
                return mMessageFragment;
            case INBOX_FRAGMENT:
                if(mInboxFragment == null) {
                    mInboxFragment = new InboxFragment();
                }
                return mInboxFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
