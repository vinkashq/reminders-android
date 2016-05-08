package io.vinkas;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.client.DataSnapshot;

import vinkas.Application;
import vinkas.io.ListItem;
import com.vinkas.reminders.NotificationReceiver;
import com.vinkas.reminders.R;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Reminder extends ListItem {

    @Override
    public void setSynced(boolean synced) {
        super.setSynced(synced);
        if(synced && isValid())
            alarm();
    }

    protected Intent getNotificationReceiver() {
        Intent receiver = new Intent(getApplication(), NotificationReceiver.class);
        receiver.setData(getList().getReceiverUri());
        receiver.setAction(getApplication().getString(R.string.reminder_action));
        receiver.putExtra(KEY, getKey());
        receiver.putExtra(TITLE, getTitle());
        receiver.putExtra(TIMESTAMP, getTimeStamp());
        return receiver;
    }

    @Override
    public Reminders getList() {
        return (Reminders) super.getList();
    }

    public static final String TITLE = "title";
    public static final String TIMESTAMP = "timestamp";
    public static final String ALARM_RTC_TYPE = "alarm_rtc_type";

    public void alarm() {
        AlarmManager am = (AlarmManager) getApplication().getSystemService(Context.ALARM_SERVICE);
        if (getPendingIntent(PendingIntent.FLAG_NO_CREATE) != null) {
            PendingIntent pc = getPendingIntent(PendingIntent.FLAG_NO_CREATE);
            pc.cancel();
            am.cancel(pc);
        }
        PendingIntent pi = getPendingIntent(PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(getAlarm_RTC_TYPE(), getTimeStamp(), pi);
    }

    protected PendingIntent getPendingIntent(int FLAG) {
        return PendingIntent.getBroadcast(getApplication(), getKey().hashCode(), getNotificationReceiver(), FLAG);
    }

    public Integer getAlarm_RTC_TYPE() {
        return Integer.parseInt(get(ALARM_RTC_TYPE));
    }

    public void setAlarm_RTC_TYPE(Integer alarm_RTC_TYPE) {
        set(ALARM_RTC_TYPE, alarm_RTC_TYPE);
    }

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

    public Reminder(Application application, Reminders reminders) {
        super(application, reminders);
        setAlarm_RTC_TYPE(AlarmManager.RTC_WAKEUP);
    }

    public Reminder(Application application, Reminders reminders, String key) {
        super(application, reminders, key);
        setAlarm_RTC_TYPE(AlarmManager.RTC_WAKEUP);
    }

    public Reminder(Application application, Reminders reminders, DataSnapshot dataSnapshot) {
        super(application, reminders, dataSnapshot);
        setAlarm_RTC_TYPE(AlarmManager.RTC_WAKEUP);
    }

    @Override
    public boolean isValid() {
        return (getTitle() != null && getTimeStamp() != null);
    }
}
