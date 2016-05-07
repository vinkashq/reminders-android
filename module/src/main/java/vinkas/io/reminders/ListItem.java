package vinkas.io.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.client.DataSnapshot;

import vinkas.reminders.NotificationReceiver;
import vinkas.reminders.R;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class ListItem extends vinkas.io.ListItem {

    @Override
    public void setSynced(boolean synced) {
        super.setSynced(synced);
        if(synced && isValid())
            alarm();
    }

    protected Intent getNotificationReceiver() {
        Intent receiver = new Intent(getContext(), NotificationReceiver.class);
        receiver.setData(getList().getReceiverUri(context));
        receiver.setAction(getContext().getString(R.string.reminder_action));
        receiver.putExtra(KEY, getKey());
        receiver.putExtra(TITLE, getTitle());
        receiver.putExtra(TIMESTAMP, getTimeStamp());
        return receiver;
    }

    @Override
    public List getList() {
        return (List) super.getList();
    }

    public static final String TITLE = "title";
    public static final String TIMESTAMP = "timestamp";

    private Context context;

    public Context getContext() {
        if (context == null)
            context = getList().getDatabase().getAndroidContext();
        return context;
    }

    public void alarm() {
        AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (getPendingIntent(PendingIntent.FLAG_NO_CREATE) != null) {
            PendingIntent pc = getPendingIntent(PendingIntent.FLAG_NO_CREATE);
            pc.cancel();
            am.cancel(pc);
        }
        PendingIntent pi = getPendingIntent(PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(ALARM_RTC_TYPE(), getTimeStamp(), pi);
    }

    protected PendingIntent getPendingIntent(int FLAG) {
        return PendingIntent.getBroadcast(getContext(), getKey().hashCode(), getNotificationReceiver(), FLAG);
    }

    public abstract int ALARM_RTC_TYPE();

    public Long getTimeStamp() {
        return Long.parseLong(get(TIMESTAMP));
    }

    public void setTimeStamp(Long timeInMillis) {
        set(TIMESTAMP, timeInMillis);
    }

    public String getTitle() {
        return get(TITLE);
    }

    public void setTitle(String title) {
        set(TITLE, title);
    }

    public ListItem(List reminders) {
        super(reminders);
    }

    public ListItem(List reminders, String key) {
        super(reminders, key);
    }

    public ListItem(List reminders, DataSnapshot dataSnapshot) {
        super(reminders, dataSnapshot);
    }

    @Override
    public boolean isValid() {
        Log.d(TITLE, getTitle());
        Log.d(TIMESTAMP, getTimeStamp().toString());
        return (getTitle() != null && getTimeStamp() != null);
    }
}
