package com.vinkas.reminders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import com.firebase.client.FirebaseError;
import com.vinkas.reminders.fragment.CreateFragment;

import io.vinkas.CreateListener;

import com.vinkas.activity.NavigationDrawerActivity;

import io.vinkas.Item;
import io.vinkas.Reminder;

/**
 * Created by Vinoth on 6-5-16.
 */
public class MainActivity extends NavigationDrawerActivity implements CreateFragment.OnFragmentInteractionListener {

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public Application getApp() {
        return (Application) super.getApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);
        setMenu(R.menu.activity_main);
        setNavigationMenu(R.menu.activity_main_drawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item) && getCurrentViewIndex() == 1) {
            onBackPressed();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    DatePickerDialog dpDialog;
    TimePickerDialog tpDialog;
    EditText etTitle, etDate, etTime;

    @Override
    public void setContent(View content) {
        super.setContent(content);
        switcher = (ViewSwitcher) getContent().findViewById(R.id.switcher);
        etTitle = (EditText) getContent().findViewById(R.id.etTitle);
        etDate = (EditText) getContent().findViewById(R.id.etDate);
        etTime = (EditText) getContent().findViewById(R.id.etTime);
        etDate.setInputType(InputType.TYPE_NULL);
        etTime.setInputType(InputType.TYPE_NULL);
        dpDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                etDate.setText(dayOfMonth + " / " + monthOfYear + " / " + year);
                tpDialog.show();
            }
        }, 2016, 10, 10);
        tpDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                etTime.setText(hourOfDay + " : " + minute);
            }
        }, 1, 1, false);
        etDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dpDialog.show();
                }
            }
        });
        etTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    tpDialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == com.vinkas.reminders.R.id.add) {
            addReminder(v);
        }
    }

    private int year, month, day, hour, min;

    public void addReminder(View v) {
        Reminder reminder = new Reminder();
        reminder.setTitle(etTitle.getText().toString());
        reminder.setTimeStamp(day, month, year, hour, min);
        reminder.setStatus(Reminder.STATUS_ACTIVE);
        getApp().getReminders().add(reminder, new CreateListener() {
            @Override
            public void onCreate(Item item) {
                showListView();
            }

            @Override
            public void onError(FirebaseError error) {

            }
        });
    }

    private int currentViewIndex = -1;
    ViewSwitcher switcher;

    public void showItemView() {
        if (getCurrentViewIndex() != 1) {
            etTitle.setText("");
            etDate.setText("");
            etTime.setText("");
            year = month = day = hour = min = 0;
            getFab().setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            switcher.showNext();
            setCurrentViewIndex(1);
        }
    }

    public void showListView() {
        if (getCurrentViewIndex() != 0) {
            getFab().setVisibility(View.VISIBLE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.syncState();
            switcher.showPrevious();
            setCurrentViewIndex(0);
        }
    }

    public int getCurrentViewIndex() {
        if (currentViewIndex == -1)
            currentViewIndex = switcher.indexOfChild(switcher.getCurrentView());
        Log.d("Index", String.valueOf(currentViewIndex));
        return currentViewIndex;
    }

    public void setCurrentViewIndex(int index) {
        this.currentViewIndex = index;
    }

    @Override
    public void onBackPressed() {
        if (getCurrentViewIndex() == 0)
            super.onBackPressed();
        else {
            showListView();
            return;
        }
    }

    @Override
    public void onFabClick(View v) {
        showItemView();
    }

}