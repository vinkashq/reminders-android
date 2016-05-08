package com.vinkas.reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.vinkas.Reminder;

/**
 * Created by Vinoth on 7-5-16.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String title = intent.getStringExtra(Reminder.TITLE);
        Long timestamp = intent.getLongExtra(Reminder.TIMESTAMP, 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(context)
                .setWhen(timestamp)
                .setShowWhen(true)
                .setContentTitle(title)
                .setContentText(title)
                .setAutoCancel(false)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();
        manager.notify(title, 0, notification);
    }

}
