package vinkas.io.reminders;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.firebase.client.Firebase;

import vinkas.reminders.R;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class List extends vinkas.io.List {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List(Database database, String type) {
        super(database, database.getRemindersPath() + type);
        read();
    }

    public Uri getReceiverUri(Context context) {
        return Uri.parse(context.getString(R.string.receiver_scheme) + "://" + context.getString(R.string.receiver_host));
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
    public void onRead(String key, Object value) {
        Log.d(key, key);
    }

}
