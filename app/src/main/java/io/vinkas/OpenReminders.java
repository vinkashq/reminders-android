package io.vinkas;

import com.vinkas.reminders.open.Application;

/**
 * Created by Vinoth on 8-5-16.
 */
public class OpenReminders implements IReminders {

    public OpenReminders(Application application, String accountId) {
        setReminders(new Reminders(application, accountId, application.getString(com.vinkas.reminders.R.string.open_reminders_key)));
    }

    private Reminders reminders;

    @Override
    public Reminders getReminders() {
        return reminders;
    }

    @Override
    public void setReminders(Reminders reminders) {
        this.reminders = reminders;
    }
}
