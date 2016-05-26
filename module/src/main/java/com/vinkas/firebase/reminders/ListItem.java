package com.vinkas.firebase.reminders;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.vinkas.firebase.*;
import com.vinkas.firebase.List;
import com.vinkas.module.reminders.R;
import com.vinkas.notifier.Scheduler;
import com.vinkas.util.Helper;

import com.google.firebase.database.Exclude;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vinoth on 6-5-16.
 */
public class ListItem extends com.vinkas.firebase.ListItem {

    public static final int STATUS_ACTIVE = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        schedule();
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

    @Exclude
    public int getAlarm_RTC_TYPE() {
        return alarm_rtc_type;
    }

    @Exclude
    public void setAlarm_rtc_type(int value) {
        alarm_rtc_type = value;
    }

    private static Class<?> contentActivity;

    public static Class<?> getContentActivity() {
        return contentActivity;
    }

    public static void setContentActivity(Class<?> contentActivity) {
        ListItem.contentActivity = contentActivity;
    }

    static Bitmap largeIcon;

    public static Bitmap getLargeIcon() {
        if (largeIcon == null)
            largeIcon = BitmapFactory.decodeResource(Helper.getApplication().getResources(), R.drawable.ic_access_alarm_white_48dp);
        return largeIcon;
    }

    public static void setLargeIcon(Bitmap icon) {
        largeIcon = icon;
    }

    @Exclude
    public Set<String> getDataSet() {
        HashSet<String> data = new HashSet<>();
        data.add(getTitle());
        data.add(getTimestamp().toString());
        return data;
    }

    private Integer notificationId = null;

    public Integer getNotificationId(boolean existingOnly) {
        if (notificationId == null || notificationId == 0)
            notificationId = getPref().getInt(getKey() + NotificationIdKey, 0);
        if (existingOnly)
            return notificationId;
        if (notificationId == 0)
            notificationId = com.vinkas.notifier.Notification.getId();
        return notificationId;
    }

    private static final String NotificationIdKey = "_NotificationId";

    public void schedule() {
        getScheduler().schedule(getNotificationId(false), getNotification(), getTimestamp(), getAlarm_RTC_TYPE());
        SharedPreferences.Editor editor = getPref().edit();
        editor.putStringSet(getKey(), getDataSet());
        editor.putInt(getKey() + NotificationIdKey, notificationId);
        editor.commit();
    }

    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) Helper.getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(getNotificationId(true));
    }

    public void unschedule() {
        Integer nid = getNotificationId(true);
        if(nid != null && nid != 0) {
            cancelNotification();
            getScheduler().unschedule(nid, getNotification());
            SharedPreferences.Editor editor = getPref().edit();
            editor.remove(getKey());
            editor.remove(getKey() + NotificationIdKey);
            notificationId = null;
            editor.commit();
        }
    }

    private Notification notification;

    @Exclude
    public Notification getNotification() {
        Notification.Builder builder = getScheduler().getNotificationBuilder();
        Intent editActivity = new Intent(getScheduler().getAndroidContext(), getContentActivity());
        editActivity.putExtra("Key", getKey());
        PendingIntent contentIndent = PendingIntent
                .getActivity(getScheduler().getAndroidContext(),
                        getNotificationId(false), editActivity, 0);
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
        return notification;
    }

    @Exclude
    public Boolean scheduleIfNotExist() {
        if (isScheduled())
            return false;
        schedule();
        return true;
    }

    @Exclude
    public Boolean isScheduled() {
        Set<String> dataSet = getPref().getStringSet(getKey(), null);
        return (dataSet != null && dataSet.containsAll(getDataSet()));
    }

    Scheduler scheduler;

    protected Scheduler getScheduler() {
        if (scheduler == null) {
            scheduler = Scheduler.getInstance();
        }
        return scheduler;
    }

    SharedPreferences pref;

    protected SharedPreferences getPref() {
        if (pref == null)
            pref = getScheduler().getAndroidContext().getSharedPreferences("Reminder", Context.MODE_PRIVATE);
        return pref;
    }

    @Override
    public void removeFrom(List list, RemoveListener listener) {
        unschedule();
        super.removeFrom(list, listener);
    }

    public ListItem() {
        super();
        setAlarm_rtc_type(AlarmManager.RTC_WAKEUP);
    }
}