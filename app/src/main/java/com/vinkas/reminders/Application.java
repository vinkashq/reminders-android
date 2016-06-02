package com.vinkas.reminders;

import android.graphics.BitmapFactory;

import com.vinkas.module.notifier.Scheduler;
import com.vinkas.reminders.util.Helper;

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
    }

}