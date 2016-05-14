package com.vinkas.reminders.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.vinkas.reminders.Application;
import com.vinkas.reminders.R;
import com.vinkas.ui.DateTimePickerDialog;
import com.vinkas.util.Helper;

import java.util.Calendar;

import io.vinkas.Reminder;

public class ItemFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Listener mListener;

    public ItemFragment() {
    }

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_reminders_item, menu);
        MenuItem delete = menu.findItem(R.id.delete);
        delete.setVisible((mode == MODE_UPDATE));
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create)
            saveReminder();
        else if (item.getItemId() == R.id.delete)
            deleteReminder();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    EditText etTitle;
    Button btAt;
    DateTimePickerDialog dt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders_create, container, false);
        initialize(view);
        return view;
    }

    public View initialize(View view) {
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        btAt = (Button) view.findViewById(R.id.btAt);
        btAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt.show();
            }
        });
        return view;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        btAt.setText(DateUtils.getRelativeDateTimeString(getContext(), dt.getTimestamp(), DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        btAt.setText(DateUtils.getRelativeDateTimeString(getContext(), dt.getTimestamp(), DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
    }

    private Reminder reminder;
    private int mode;
    public static final int MODE_CREATE = 0;
    public static final int MODE_UPDATE = 1;
    public static final int MODE_DELETE = 2;

    public void setDateTimePicker(long timestamp) {
        dt = new DateTimePickerDialog(getContext(), this, this, timestamp);
        Calendar c = dt.getCalendar();
        onDateSet(null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        onTimeSet(null, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
    }

    public void prepareEdit(Reminder reminder) {
        this.reminder = reminder;
        mode = MODE_UPDATE;
        setDateTimePicker(reminder.getTimestamp());
        etTitle.setText(reminder.getTitle());
    }

    public void prepareNew() {
        reminder = new Reminder();
        mode = MODE_CREATE;
        etTitle.setText("");
        setDateTimePicker(System.currentTimeMillis() + (1000 * 60 * 60 * 24) + (1000 * 60 * 10));
    }

    public void saveReminder() {
        reminder.setTitle(etTitle.getText().toString());
        reminder.setTimestamp(dt.getTimestamp());
        reminder.setStatus(Reminder.STATUS_ACTIVE);
        ((Application) Helper.getApplication()).getReminders().add(reminder, null);
        mListener.onSave(mode, reminder);
        prepareNew();
    }

    public void deleteReminder() {
        ((Application) Helper.getApplication()).getReminders().remove(reminder, null);
        mListener.onSave(MODE_DELETE, reminder);
        prepareNew();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface Listener {
        void onSave(int mode, Reminder reminder);
        void onFragmentInteraction(Uri uri);
    }
}
