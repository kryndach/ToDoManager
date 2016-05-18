package io.blackbricks.todomanager;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.background.AlarmReceiver;
import io.blackbricks.todomanager.dagger.DaggerToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.functions.Action1;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class ToDoManagerApp extends Application {

    private RefWatcher refWatcher;

    private static ToDoManagerAppComponent appComponent;

    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerToDoManagerAppComponent.builder()
                .toDoManagerModule(new ToDoManagerModule(this))
                .databaseModule(new DatabaseModule())
                .build();
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        ToDoManagerApp application = (ToDoManagerApp) context.getApplicationContext();
        return application.refWatcher;
    }

    public static ToDoManagerAppComponent getAppComponent() {
        return appComponent;
    }

    public static ToDoManagerApp get(Context context) {
        return (ToDoManagerApp) context.getApplicationContext();
    }
}
