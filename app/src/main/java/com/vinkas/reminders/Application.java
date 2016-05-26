package com.vinkas.reminders;

import android.graphics.BitmapFactory;

import com.vinkas.notifier.Scheduler;
import com.vinkas.reminders.util.Helper;

import com.vinkas.firebase.reminders.ListItem;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends com.vinkas.app.Application {

    @Override
    public void setHelper() {
        Helper.setApplication(this);
        setHelper(new Helper(getResources().getBoolean(R.bool.firebase_persistence_enabled)));
    }

    @Override
    public Helper getHelper() {
        return (Helper) super.getHelper();
    }

    @Override
    public void onFirebaseCreate() {
        super.onFirebaseCreate();
        Scheduler.setAndroidContext(getApplicationContext());
        ListItem.setContentActivity(MainActivity.class);
        ListItem.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
    }
}