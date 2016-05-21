package com.vinkas.reminders;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.vinkas.util.Helper;

import com.vinkas.firebase.reminders.ListItem;

/**
 * Created by Vinoth on 12-5-16.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDateTime, tvTitle;
    private ImageView ivIcon;

    private String key, title;
    private long timestamp;
    private LinearLayout linearLayout;

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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(title.substring(0,1), ColorGenerator.MATERIAL.getColor(title.hashCode()));
        ivIcon.setImageDrawable(myDrawable);
    }

    private ListItem listItem;

    public ListItem getListItem() {
        return listItem;
    }

    public void setListItem(ListItem listItem) {
        this.listItem = listItem;
        setKey(listItem.getKey());
        setTitle(listItem.getTitle());
        setTimestamp(listItem.getTimestamp());
    }

    public void setOnClickListener(final OnClickListener listener) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, getListItem());
            }
        });
    }

    public ViewHolder(View itemView) {
        super(itemView);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.list_item);
        tvTitle = (TextView) itemView.findViewById(R.id.title);
        tvDateTime = (TextView) itemView.findViewById(R.id.datetime);
        ivIcon = (ImageView) itemView.findViewById(R.id.icon);
    }

    public interface OnClickListener {
        void onClick(View view, ListItem listItem);
    }
}