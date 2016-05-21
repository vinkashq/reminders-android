package com.vinkas.reminders;

import com.vinkas.reminders.util.Helper;

import com.vinkas.firebase.reminders.ListItem;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends com.vinkas.app.Application {

    @Override
    public void setHelper() {
        Helper.setApplication(this);
        setHelper(new Helper());
    }

    @Override
    public Helper getHelper() {
        return (Helper) super.getHelper();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ListItem.setContentActivity(MainActivity.class);
    }

}