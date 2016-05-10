package com.vinkas.reminders;

import io.vinkas.IReminders;
import io.vinkas.Reminders;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends com.vinkas.library.Application implements IReminders {

    private Reminders reminders;

    @Override
    public Reminders getReminders() {
        return reminders;
    }

    @Override
    public void setReminders(Reminders reminders) {
        this.reminders = reminders;
    }

    @Override
    public void setUserId(String userId) {
        super.setUserId(userId);
        setReminders(new Reminders("open"));
    }

}
