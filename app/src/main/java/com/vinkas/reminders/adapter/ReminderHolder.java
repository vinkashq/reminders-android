package com.vinkas.reminders.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.vinkas.reminders.R;
import com.vinkas.util.Helper;

import java.util.Calendar;

/**
 * Created by Vinoth on 12-5-16.
 */
public class ReminderHolder extends RecyclerView.ViewHolder {

    private TextView tvDateTime, tvTitle;
    private ImageView ivIcon;

    private String key, title;
    private long timestamp;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        CharSequence s = DateUtils.getRelativeDateTimeString(Helper.getApplication().getApplicationContext(), timestamp, DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        tvDateTime.setText(s);
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(title.substring(0,1), ColorGenerator.MATERIAL.getColor(title.hashCode()));
        ivIcon.setImageDrawable(myDrawable);
    }

    public ReminderHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.title);
        tvDateTime = (TextView) itemView.findViewById(R.id.datetime);
        ivIcon = (ImageView) itemView.findViewById(R.id.icon);
    }
}
