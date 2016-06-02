package com.vinkas.firealm.model;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.google.firebase.database.Exclude;
import com.vinkas.realm.model.Notification;
import com.vinkas.module.reminder.R;
import com.vinkas.module.notifier.Scheduler;
import com.vinkas.util.Helper;

import org.firealm.FirealmModel;
import org.firealm.FirealmProperty;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vinoth on 1-6-16.
 */
public class Reminder extends RealmObject implements FirealmModel {

    private static Class<?> contentActivity;
    static Bitmap largeIcon;

    public static Bitmap getLargeIcon() {
        if (largeIcon == null)
            largeIcon = BitmapFactory.decodeResource(Helper.getApplication().getResources(), R.drawable.ic_access_alarm_white_48dp);
        return largeIcon;
    }

    public static void setLargeIcon(Bitmap icon) {
        largeIcon = icon;
    }

    public static Class<?> getContentActivity() {
        return contentActivity;
    }

    public static void setContentActivity(Class<?> activity) {
        contentActivity = activity;
    }

    public Reminder() {
        setNotification(new Notification());
        setFirealmProperty(new FirealmProperty());
    }

    private FirealmProperty firealmProperty;

    @Override
    public FirealmProperty firealmProperty() {
        return firealmProperty;
    }

    public void setFirealmProperty(FirealmProperty value) {
        firealmProperty = value;
    }

    @PrimaryKey
    private Integer id;
    private String title;
    private Integer status;
    private Notification notification;

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
        return getNotification().getTimestamp();
    }

    public void setTimestamp(Long value) {
        getNotification().setTimestamp(value);
        firealmProperty().setPriority(value);
    }

    @Exclude
    public Integer getId() {
        return id;
    }

    protected void setId(Integer value) {
        id = value;
        firealmProperty().setKey(id.toString());
    }

    @Exclude
    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    protected Scheduler getScheduler() {
        return Scheduler.getInstance();
    }

    @Exclude
    public android.app.Notification getAndroidNotification() {
        android.app.Notification notification;
        android.app.Notification.Builder builder = getScheduler().getNotificationBuilder();
        Intent editActivity = new Intent(getScheduler().getAndroidContext(), getContentActivity());
        editActivity.putExtra("Key", firealmProperty().getKey());
        PendingIntent contentIndent = PendingIntent
                .getActivity(getScheduler().getAndroidContext(),
                        getId(), editActivity, 0);
        builder = builder.setWhen(getTimestamp())
                .setContentTitle(getTitle())
                //.setContentText(getTitle())
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(contentIndent)
                .setSmallIcon(R.drawable.ic_access_alarm_white_24dp)
                .setLargeIcon(getLargeIcon())
                .setDefaults(android.app.Notification.DEFAULT_SOUND);
        if (Build.VERSION.SDK_INT >= 17)
            builder.setShowWhen(true);
        if (Build.VERSION.SDK_INT >= 16)
            notification = builder.build();
        else
            notification = builder.getNotification();
        return notification;
    }

}