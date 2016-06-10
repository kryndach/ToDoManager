package io.blackbricks.todomanager;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.blackbricks.todomanager.api.ApiConstants;
import io.blackbricks.todomanager.api.NetModule;
import io.blackbricks.todomanager.dagger.AppComponent;
import io.blackbricks.todomanager.dagger.AppModule;
import io.blackbricks.todomanager.dagger.DaggerAppComponent;
import io.blackbricks.todomanager.database.DatabaseModule;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class ToDoManagerApp extends Application {

    private RefWatcher refWatcher;

    private static AppComponent appComponent;
    private static Context mContext;

    @Override public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .databaseModule(new DatabaseModule())
                .netModule(new NetModule(ApiConstants.API_BASE_URL, ApiConstants.APP_KEY))
                .build();
        refWatcher = LeakCanary.install(this);
        mContext = this;
    }

    public static RefWatcher getRefWatcher(Context context) {
        ToDoManagerApp application = (ToDoManagerApp) context.getApplicationContext();
        return application.refWatcher;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static ToDoManagerApp get(Context context) {
        return (ToDoManagerApp) context.getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
