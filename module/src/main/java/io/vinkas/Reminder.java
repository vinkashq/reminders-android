package io.vinkas;

import android.app.Notification;
import android.os.Build;

import com.firebase.client.Firebase;
import com.vinkas.notification.Scheduler;
import com.vinkas.util.Helper;

import java.util.Calendar;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Reminder extends ListItem<Reminders> {

    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_INACTIVE = 2;
    public static final int STATUS_ACHIEVED = 3;

    public void setTimeStamp(int day, int month, int year, int hour, int min) {
        Helper helper = Helper.getInstance();
        setTimestamp(helper.toTimeStamp(day, month, year, hour, min));
    }

    private String title;
    private Long timestamp;
    private Integer rtc_type, status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRtc_type() {
        return rtc_type;
    }

    public void setRtc_type(Integer rtc_type) {
        this.rtc_type = rtc_type;
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

    public void schedule() {
        Scheduler scheduler = Scheduler.getInstance();
        Notification.Builder builder = scheduler.getNotificationBuilder();
        Notification notification;
        builder = builder.setWhen(getTimestamp())
                .setContentTitle(getTitle())
                .setContentText(getTitle())
                .setAutoCancel(false)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setDefaults(Notification.DEFAULT_SOUND);
        if (Build.VERSION.SDK_INT >= 17)
            builder.setShowWhen(true);
        if (Build.VERSION.SDK_INT >= 16)
            notification = builder.build();
        else
            notification = builder.getNotification();
        scheduler.schedule(getKey().hashCode(), notification, getTimestamp(), getRtc_type());
    }

    public Reminder() {
        super();
    }
}