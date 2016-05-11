package io.vinkas;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;
import com.vinkas.util.Helper;

/**
 * Created by Vinoth on 6-5-16.
 */
public class Reminders extends List<Reminder> {

    public Reminders(Repo repo, Path path) {
        super(repo, path);
    }

    public Reminders(String type) {
        super(Helper.getUserUrl("reminders/" + type));
    }

    public void create(String title, long timestamp, int status, final CreateListener<Reminder> listener) {
        final Reminder reminder = new Reminder();
        reminder.setTitle(title);
        reminder.setTimestamp(timestamp);
        reminder.setStatus(status);
        reminder.writeTo(this, new CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    reminder.schedule();
                    listener.onCreate(reminder);
                }
                else
                    listener.onError(firebaseError);
            }
        });
    }
}
