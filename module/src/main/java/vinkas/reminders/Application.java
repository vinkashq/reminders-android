package vinkas.reminders;

import vinkas.io.reminders.Database;

/**
 * Created by Vinoth on 5-5-16.
 */
public abstract class Application extends vinkas.Application {

    @Override
    public Database getDatabase() {
        return (Database) super.getDatabase();
    }

}
