package vinkas.io.reminders;

import com.firebase.client.DataSnapshot;

import java.util.Date;

/**
 * Created by Vinoth on 6-5-16.
 */
public class ListItem extends vinkas.io.ListItem {

    public Date getAt() {
        Long l = Long.getLong(get("At"));
        return new Date(l);
    }

    public void setAt(Date dateTime) {
        set("At", dateTime);
    }

    public String getTitle() {
        return get("Title");
    }

    public void setTitle(String title) {
        set("Title", title);
    }

    public ListItem(List reminders) {
        super(reminders);
    }

    public ListItem(List reminders, String key) {
        super(reminders, key);
    }

    public ListItem(DataSnapshot dataSnapshot) {
        super(dataSnapshot);
    }

    @Override
    public boolean isValid() {
        return (getTitle() != null && getAt() != null);
    }
}
