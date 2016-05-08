package com.vinkas.reminders.open;

import vinkas.model.reminders.open.Database;
import vinkas.io.OpenReminders;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Activity extends vinkas.app.Activity {

    public OpenReminders getReminders() {
        return getDatabase().getReminders();
    }

    @Override
    public Application getApp() {
        return (Application) super.getApp();
    }

    @Override
    public Database getDatabase() {
        return (Database) super.getDatabase();
    }
}
