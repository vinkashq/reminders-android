package io.vinkas;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.ui.RecyclerAdapter;
import com.vinkas.activity.Activity;
import com.vinkas.module.reminders.R;
import com.vinkas.notification.Scheduler;
import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Reminder extends ListItem<Reminders> {

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 2;
    public static final int STATUS_ACHIEVED = 3;

    @Override
    public void onCreate() {
        super.onCreate();
        schedule();
    }

    public void setTimestamp(int day, int month, int year, int hour, int min) {
        Helper helper = Helper.getInstance();
        setTimestamp(helper.toTimeStamp(day, month, year, hour, min));
    }

    private String title;
    private Long timestamp;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }


    private int alarm_rtc_type;

    @JsonIgnore
    public int getAlarm_RTC_TYPE() {
        return alarm_rtc_type;
    }

    @JsonIgnore
    public void setAlarm_rtc_type(int value) {
        alarm_rtc_type = value;
    }

    private static Class<?> contentActivity;

    public static Class<?> getContentActivity() {
        return contentActivity;
    }

    public static void setContentActivity(Class<?> contentActivity) {
        Reminder.contentActivity = contentActivity;
    }

    static Bitmap largeIcon;

    public static Bitmap getLargeIcon() {
        if (largeIcon == null)
            largeIcon = BitmapFactory.decodeResource(Helper.getApplication().getResources(), R.drawable.ic_access_alarm_white_48dp);
        return largeIcon;
    }

    public void schedule() {
        Notification.Builder builder = getScheduler().getNotificationBuilder();
        Notification notification;
        Intent editActivity = new Intent(getScheduler().getAndroidContext(), getContentActivity());
        editActivity.putExtra("Key", getKey());
        PendingIntent contentIndent = PendingIntent
                .getActivity(getScheduler().getAndroidContext(),
                        getKey().hashCode(), editActivity, 0);
        builder = builder.setWhen(getTimestamp())
                .setContentTitle(getTitle())
                //.setContentText(getTitle())
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(contentIndent)
                .setSmallIcon(R.drawable.ic_access_alarm_white_24dp)
                .setLargeIcon(getLargeIcon())
                .setDefaults(Notification.DEFAULT_SOUND);
        if (Build.VERSION.SDK_INT >= 17)
            builder.setShowWhen(true);
        if (Build.VERSION.SDK_INT >= 16)
            notification = builder.build();
        else
            notification = builder.getNotification();
        getScheduler().schedule(getKey().hashCode(), notification, getTimestamp(), getAlarm_RTC_TYPE());

        SharedPreferences.Editor editor = getPref().edit();
        editor.putLong(getKey(), System.currentTimeMillis());
        editor.putString(getKey() + "_Title", getTitle());
        editor.putLong(getKey() + "_Timestamp", getTimestamp());
        editor.commit();
    }

    public Boolean scheduleIfNotExist() {
        if(isScheduled())
            return false;
        schedule();
        return true;
    }

    @JsonIgnore
    public Boolean isScheduled() {
        if(getPref().contains(getKey())) {
            if(getPref().getString(getKey() + "_Title", "").equals(getTitle()) &&
                    getPref().getLong(getKey() + "_Timestamp", 0) == getTimestamp())
                return true;
        }
        return false;
    }

    Scheduler scheduler;
    protected Scheduler getScheduler() {
        if(scheduler == null) {
            scheduler = Scheduler.getInstance();
        }
        return scheduler;
    }

    SharedPreferences pref;
    protected SharedPreferences getPref() {
        if(pref == null)
            pref = getScheduler().getAndroidContext().getSharedPreferences("Reminder", Context.MODE_PRIVATE);
        return pref;
    }

    public Reminder() {
        super();
        setAlarm_rtc_type(AlarmManager.RTC_WAKEUP);
    }
}