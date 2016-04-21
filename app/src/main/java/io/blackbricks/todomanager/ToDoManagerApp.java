package io.blackbricks.todomanager;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import io.blackbricks.todomanager.dagger.DaggerToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;

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
}
