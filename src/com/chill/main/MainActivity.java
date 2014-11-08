package com.chill.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.chill.R;
import com.chill.main.contracts.NavigationListener;

public class MainActivity extends FragmentActivity implements NavigationListener {

    private ViewPager mViewPager;
    private FragmentPageAdapter mPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPageAdapter = new FragmentPageAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setCurrentItem(FragmentPageAdapter.MESSAGE_FRAGMENT);
    }

    @Override
    public void onClickOutbox() {
        mViewPager.setCurrentItem(FragmentPageAdapter.OUTBOX_FRAGMENT);
    }

    @Override
    public void onClickMessage() {
        mViewPager.setCurrentItem(FragmentPageAdapter.MESSAGE_FRAGMENT);
    }

    @Override
    public void onClickInbox() {
        mViewPager.setCurrentItem(FragmentPageAdapter.INBOX_FRAGMENT);
    }
}