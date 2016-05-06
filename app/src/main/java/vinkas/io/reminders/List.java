package vinkas.io.reminders;

import android.util.Log;

import com.firebase.client.Firebase;

/**
 * Created by Vinoth on 6-5-16.
 */
public class List extends vinkas.io.List {

    public List(Database database, String accountId) {
        super(database, childPath(accountId));
        read();
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

    private static String childPath(String accountId) {
        return "reminders/open/" + accountId;
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
