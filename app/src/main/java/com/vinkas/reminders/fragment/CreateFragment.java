package com.vinkas.reminders.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.firebase.client.FirebaseError;
import com.vinkas.reminders.Application;
import com.vinkas.reminders.MainActivity;
import com.vinkas.reminders.R;
import com.vinkas.util.Helper;

import io.vinkas.CreateListener;
import io.vinkas.Item;
import io.vinkas.Reminder;

public class CreateFragment extends Fragment {
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

    DatePickerDialog dpDialog;
    TimePickerDialog tpDialog;
    EditText etTitle;
    Button btDate;
    TextView btTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders_create, container, false);
        initialize(view);
        return view;
    }

    public View initialize(View view) {
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        btDate = (Button) view.findViewById(R.id.btDate);
        btTime = (TextView) view.findViewById(R.id.btTime);
        dpDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                CreateFragment.this.year = year;
                month = monthOfYear + 1;
                day = dayOfMonth;
                btDate.setText(String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year);
                tpDialog.show();
            }
        }, 2016, 10, 10);
        tpDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String am_pm = "AM";
                hour = hourOfDay;
                if (hourOfDay >= 12) {
                    hour = hour - 12;
                    am_pm = "PM";
                }
                if (hour == 0)
                    hour = 12;
                min = minute;
                btTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + " " + am_pm);
            }
        }, 1, 1, false);
        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpDialog.show();
            }
        });
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpDialog.show();
            }
        });
        return view;
    }

    private int year, month, day, hour, min;

    public void addReminder() {
        Reminder reminder = new Reminder();
        reminder.setTitle(etTitle.getText().toString());
        reminder.setTimeStamp(day, month - 1, year, hour, min);
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
