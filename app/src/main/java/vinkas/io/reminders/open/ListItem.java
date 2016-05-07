package vinkas.io.reminders.open;

import android.app.AlarmManager;

import com.firebase.client.DataSnapshot;

import vinkas.io.reminders.*;
import vinkas.io.reminders.List;

/**
 * Created by Vinoth on 7-5-16.
 */
public class ListItem extends vinkas.io.reminders.ListItem {

    public ListItem(vinkas.io.reminders.List reminders) {
        super(reminders);
    }

    public ListItem(List reminders, DataSnapshot dataSnapshot) {
        super(reminders, dataSnapshot);
    }

    public ListItem(List reminders, String key) {
        super(reminders, key);
    }

    @Override
    public int ALARM_RTC_TYPE() {
        return AlarmManager.RTC_WAKEUP;
    }

}
