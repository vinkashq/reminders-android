package com.vinkas.reminders.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vinkas.reminders.R;

/**
 * Created by Vinoth on 12-5-16.
 */
public class ReminderHolder extends RecyclerView.ViewHolder {

    public TextView tvDateTime;
    public CheckBox cbTitle;

    public ReminderHolder(View itemView) {
        super(itemView);
        cbTitle = (CheckBox) itemView.findViewById(R.id.title);
        tvDateTime = (TextView) itemView.findViewById(R.id.datetime);
    }
}
