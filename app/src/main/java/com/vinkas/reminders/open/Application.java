package com.vinkas.reminders.open;

import io.vinkas.IReminders;
import io.vinkas.Reminder;
import io.vinkas.Reminders;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends com.vinkas.library.Application implements IReminders {

    private Reminders reminders;

    @Override
    public void onCreate() {
        super.onCreate();
        Reminder.setContentActivity(MainActivity.class);
    }

    @Override
    public Reminders getReminders() {
        return reminders;
    }

    @Override
    public void setReminders(Reminders value) {
        reminders = value;
        reminders.keepSynced(true);
    }

    @Override
    public void setUserId(String userId) {
        super.setUserId(userId);
        setReminders(new Reminders("open"));
    }

}
