package vinkas.io.reminders.open;

import com.firebase.client.DataSnapshot;
import com.vinkas.reminders.R;

import java.util.Calendar;

/**
 * Created by Vinoth on 7-5-16.
 */
public class List extends vinkas.io.reminders.List {

    public List(Database database) {
        super(database, database.getAndroidContext().getString(R.string.firebase_reminders_type));
    }

    public ListItem create(String title, int day, int month, int year, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        return create(title, calendar.getTimeInMillis());
    }

    public ListItem create(String title, Long timeInMillis) {
        ListItem i = new ListItem(this);
        i.setTitle(title);
        i.setTimeStamp(timeInMillis);
        i.write();
        return i;
    }

    @Override
    public ListItem getItem(DataSnapshot dataSnapshot) {
        return new ListItem(this, dataSnapshot);
    }

}
