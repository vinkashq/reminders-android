package com.vinkas.reminders;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.vinkas.reminders.fragment.ItemFragment;

import com.vinkas.activity.NavigationDrawerActivity;
import com.vinkas.reminders.fragment.ListFragment;
import com.vinkas.util.Helper;

import io.vinkas.Reminder;

/**
 * Created by Vinoth on 6-5-16.
 */
public class MainActivity extends NavigationDrawerActivity implements ItemFragment.Listener, ListFragment.Listener, ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                itemFragment.prepareNew();
                getFab().setVisibility(View.VISIBLE);
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(false);
                getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                toggle.syncState();
                break;
            case 1:
                getFab().setVisibility(View.GONE);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
                getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(Reminder reminder) {
        itemFragment.prepareEdit(reminder);
        mViewPager.setCurrentItem(1);
    }

    public ItemFragment getItemFragment() {
        if (itemFragment == null)
            itemFragment = ItemFragment.newInstance();
        return itemFragment;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return ListFragment.newInstance(1);
            else
                return getItemFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Reminders";
                case 1:
                    return "Reminder";
            }
            return null;
        }
    }

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSave(int mode, Reminder reminder) {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public Application getApp() {
        return (Application) super.getApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        setMenu(R.menu.activity_main);
        setNavigationMenu(R.menu.activity_main_drawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item) && mViewPager.getCurrentItem() == 1) {
            mViewPager.setCurrentItem(0);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContent(View content) {
        super.setContent(content);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        String key = getIntent().getStringExtra("Key");
        if (key != null) {
            getApp().getReminders().child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Reminder item = snapshot.getValue(Reminder.class);
                        item.setKey(snapshot.getKey());
                        item.setPriority(snapshot.getPriority());
                        onItemClick(item);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Helper.onError(firebaseError);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0)
            super.onBackPressed();
        else {
            mViewPager.setCurrentItem(0);
            return;
        }
    }

    ItemFragment itemFragment;

    @Override
    public void onFabClick(View v) {
        itemFragment.prepareNew();
        mViewPager.setCurrentItem(1);
    }

}