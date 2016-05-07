package vinkas.io.reminders;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import vinkas.io.Account;
import vinkas.reminders.R;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class Database extends vinkas.io.Database {

    public Database(Context context, boolean persistenceEnabled, String host) {
        super(context, persistenceEnabled, host);
    }

    @Override
    public void setAccountId(String accountId) {
        super.setAccountId(accountId);
        setRemindersPath();
    }

    public abstract void setReminders();

    private List reminders;
    private String remindersPath;

    public String getRemindersPath() {
        return remindersPath;
    }

    public void setRemindersPath() {
        setRemindersPath(getAndroidContext().getString(R.string.firebase_reminders_path).replace("ACCOUNT_ID", getAccountId()));
        setReminders();
    }

    public void setRemindersPath(String path) {
        this.remindersPath = path;
    }

    public List getReminders() {
        return reminders;
    }

    public void setReminders(List reminders) {
        this.reminders = reminders;
    }

}