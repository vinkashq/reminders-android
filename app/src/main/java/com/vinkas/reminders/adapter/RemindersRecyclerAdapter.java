package com.vinkas.reminders.adapter;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import io.vinkas.Reminder;
import io.vinkas.ui.RecyclerAdapter;

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
        viewHolder.setKey(model.getKey());
        viewHolder.setTitle(model.getTitle());
        viewHolder.setTimestamp(model.getTimestamp());
        //viewHolder.tvDateTime.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " " + calendar.get(Calendar.AM_PM));
    }
}
