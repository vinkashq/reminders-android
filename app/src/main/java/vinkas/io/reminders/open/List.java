package vinkas.io.reminders.open;

import com.firebase.client.DataSnapshot;
import com.vinkas.reminders.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import vinkas.io.reminders.Database;
import vinkas.io.reminders.ListItem;

/**
 * Created by Vinoth on 7-5-16.
 */
public class List extends vinkas.io.reminders.List {

    public List(Database database) {
        super(database, database.getAndroidContext().getString(R.string.firebase_reminders_type));
    }

    public ListItem create(String title, int day, int month, int year, int hour, int min) throws ParseException {
        ListItem i = new ListItem(this);
        i.setTitle(title);
        i.setAt(new SimpleDateFormat("d/M/yyyy h:m").parse(day + "/" + month + "/" + year + " " + hour + ":" + min));
        i.write();
        return i;
    }

    @Override
    public ListItem getItem(DataSnapshot dataSnapshot) {
        ListItem li = new ListItem(this, dataSnapshot.getKey());
        li.onDataChange(dataSnapshot);
        return li;
    }

}
