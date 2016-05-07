package com.vinkas.reminders;

import vinkas.io.reminders.open.Database;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends vinkas.reminders.Application {

    @Override
    public void setDatabase() {
        setDatabase(new Database(getApplicationContext(), getResources().getBoolean(vinkas.reminders.R.bool.firebase_persistence_enabled), getString(vinkas.reminders.R.string.firebase_io_hostname)));
    }

    @Override
    public Database getDatabase() {
        return (Database) super.getDatabase();
    }
}
