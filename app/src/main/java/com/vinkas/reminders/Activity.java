package com.vinkas.reminders;

import vinkas.io.reminders.open.Database;
import vinkas.io.reminders.open.List;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Activity extends vinkas.app.Activity {

    public List getReminders() {
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
