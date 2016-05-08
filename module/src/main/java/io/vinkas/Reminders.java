package io.vinkas;

import android.net.Uri;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;

import java.util.Calendar;

import vinkas.Application;
import vinkas.io.List;
import com.vinkas.reminders.R;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Reminders extends List {

    public Reminder create(String title, int day, int month, int year, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        return create(title, calendar.getTimeInMillis());
    }

    public Reminder create(String title, Long timeInMillis) {
        Reminder i = new Reminder(getApplication(), this);
        i.setTitle(title);
        i.setTimeStamp(timeInMillis);
        i.write();
        return i;
    }

    @Override
    public Reminder getItem(DataSnapshot dataSnapshot) {
        return new Reminder(getApplication(), this, dataSnapshot);
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Reminders(Application application, String accountId, String type) {
        super(application, getChildPath(application, accountId, type));
        read();
    }

    public static String getChildPath(Application application, String accountId, String type) {
        return application.getString(R.string.firebase_reminders_path).replace("ACCOUNT_ID", accountId) + type;
    }

    public Uri getReceiverUri() {
        return Uri.parse(getApplication().getString(R.string.receiver_scheme) + "://" + getApplication().getString(R.string.receiver_host));
    }

    @Override
    protected void setFirebase(Firebase firebase) {
        super.setFirebase(firebase);
        firebase.keepSynced(true);
    }

    @Override
    public Firebase getFirebase() {
        return super.getFirebase();
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void onRead(String key, java.lang.Object value) {
        Log.d(key, key);
    }

}
