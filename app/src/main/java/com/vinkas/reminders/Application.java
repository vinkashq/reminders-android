package com.vinkas.reminders;

import vinkas.io.reminders.Database;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends vinkas.Application {

    @Override
    public Database getDatabase() {
        return (Database) super.getDatabase();
    }

    @Override
    public void setDatabase() {
        setDatabase(new Database(getApplicationContext(), getResources().getBoolean(R.bool.persistence_enabled), getString(R.string.firebase_io_hostname)));
    }
}
