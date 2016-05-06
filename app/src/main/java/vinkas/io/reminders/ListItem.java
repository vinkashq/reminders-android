package vinkas.io.reminders;

import java.util.Date;

/**
 * Created by Vinoth on 6-5-16.
 */
public class ListItem extends vinkas.io.ListItem {

    public Date getDateTime() {
        Long l = Long.getLong(get("DateTime"));
        return new Date(l);
    }

    public void setDateTime(Date dateTime) {
        set("DateTime", dateTime);
    }

    public String getTitle() {
        return get("Title");
    }

    public void setTitle(String title) {
        set("Title", title);
    }

    public ListItem(List reminders) {
        super(reminders);
        getFirebase().setPriority(0 - System.currentTimeMillis());
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
