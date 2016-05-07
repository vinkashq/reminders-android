package vinkas.io.reminders.open;

import android.content.Context;

/**
 * Created by Vinoth on 7-5-16.
 */
public class Database extends vinkas.io.reminders.Database {

    @Override
    public void setReminders() {
        setReminders(new List(this));
    }

    public Database(Context context, boolean persistenceEnabled, String host) {
        super(context, persistenceEnabled, host);
    }

    @Override
    public List getReminders() {
        return (List) super.getReminders();
    }
}
