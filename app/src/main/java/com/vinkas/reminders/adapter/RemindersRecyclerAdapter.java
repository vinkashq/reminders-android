package com.vinkas.reminders.adapter;

import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateUtils;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.RecyclerAdapter;

import java.util.Calendar;

import io.vinkas.Reminder;

/**
 * Created by Vinoth on 12-5-16.
 */
public class RemindersRecyclerAdapter extends RecyclerAdapter<Reminder, ReminderHolder> {

    public RemindersRecyclerAdapter(Class<Reminder> modelClass, int modelLayout, Class<ReminderHolder> viewHolderClass, Firebase ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    public RemindersRecyclerAdapter(Class<Reminder> modelClass, int modelLayout, Class<ReminderHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(ReminderHolder viewHolder, Reminder model, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(model.getTimestamp());
        viewHolder.cbTitle.setText(model.getTitle());
        CharSequence s = DateUtils.getRelativeTimeSpanString(model.getTimestamp(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        viewHolder.tvDateTime.setText(s);
        //viewHolder.tvDateTime.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " " + calendar.get(Calendar.AM_PM));
    }
}
