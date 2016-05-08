package com.vinkas.reminders.open;

import io.vinkas.OpenReminders;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Application extends vinkas.Application {

    private OpenReminders openReminders;

    public OpenReminders getOpenReminders() {
        return openReminders;
    }

    public void setOpenReminders(OpenReminders openReminders) {
        this.openReminders = openReminders;
    }

    @Override
    public void setAccountId(String accountId) {
        super.setAccountId(accountId);
        setOpenReminders(new OpenReminders(this, accountId));
    }

}
