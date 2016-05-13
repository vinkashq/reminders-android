package com.vinkas.reminders.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.TimePicker;

import com.vinkas.reminders.Application;
import com.vinkas.reminders.R;
import com.vinkas.ui.DateTimePickerDialog;
import com.vinkas.util.Helper;

import java.util.Calendar;

import io.vinkas.Reminder;

public class CreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Listener mListener;

    public CreateFragment() {
    }

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_reminders_create, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create)
            addReminder();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        dt = new DateTimePickerDialog(getContext(), this, this, System.currentTimeMillis() + (1000 * 60 * 60 * 25));
        Calendar c = dt.getCalendar();
        onDateSet(null, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        onTimeSet(null, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
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
        //btDate.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", monthOfYear + 1) + "/" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        btAt.setText(DateUtils.getRelativeDateTimeString(getContext(), dt.getTimestamp(), DateUtils.MINUTE_IN_MILLIS, DateUtils.YEAR_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL));
        /*String am_pm = "AM";

        if (hourOfDay >= 12) {
            hourOfDay = hourOfDay - 12;
            am_pm = "PM";
        }
        if (hourOfDay == 0)
            hourOfDay = 12;
        btTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute) + " " + am_pm);*/
    }

    public void addReminder() {
        Reminder reminder = new Reminder();
        reminder.setTitle(etTitle.getText().toString());
        reminder.setTimestamp(dt.getTimestamp());
        reminder.setStatus(Reminder.STATUS_ACTIVE);
        ((Application) Helper.getApplication()).getReminders().add(reminder, null);
        mListener.onCreate(reminder);
        etTitle.setText("");
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
        void onCreate(Reminder reminder);

        void onFragmentInteraction(Uri uri);
    }
}
