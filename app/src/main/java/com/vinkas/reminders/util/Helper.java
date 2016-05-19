package com.vinkas.reminders.util;

import com.vinkas.reminders.Application;

import io.vinkas.IReminders;
import io.vinkas.Reminders;

/**
 * Created by Vinoth on 19-5-16.
 */
public class Helper extends com.vinkas.util.Helper implements IReminders {

    public static Helper getInstance() {
        return getApplication().getHelper();
    }

    public static Application getApplication() {
        return (Application) application;
    }

    private Reminders reminders;

    @Override
    public Reminders getReminders() {
        return reminders;
    }

    @Override
    public void setReminders(Reminders items) {
        reminders = items;
    }

    @Override
    public void onReady(String userId) {
        super.onReady(userId);
        setReminders(new Reminders("reminders", userId));
    }
}
