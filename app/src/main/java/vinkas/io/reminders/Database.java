package vinkas.io.reminders;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import vinkas.io.Account;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Database extends vinkas.io.Database {

    public Database(Context context, boolean persistenceEnabled, String host) {
        super(context, persistenceEnabled, host);
    }

    @Override
    protected void setAccount(Account account) {
        super.setAccount(account);
        setReminders(new List(this, account.getId()));
    }

    private List reminders;

    public List getReminders() {
        return reminders;
    }

    public void setReminders(List reminders) {
        this.reminders = reminders;
    }

    public ListItem create(String title, int day, int month, int year, int hour, int min) throws ParseException {
        ListItem i = new ListItem(getReminders());
        i.setTitle(title);
        i.setDateTime(new SimpleDateFormat("d/M/yyyy h:m").parse(day + "/" + month + "/" + year + " " + hour + ":" + min));
        i.write();
        return i;
    }

}
