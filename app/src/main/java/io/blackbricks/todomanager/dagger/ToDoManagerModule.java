package io.blackbricks.todomanager.dagger;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.api.ToDoApi;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by yegorkryndach on 15/04/16.
 */

@Module
public class ToDoManagerModule {

    private static final String END_POINT = "http://52.40.140.90:80";
    private static final String APP_KEY = "a8536db6-2230-4a4d-9086-0e500fcd760f";

    private final ToDoManagerApp app;

    public ToDoManagerModule(ToDoManagerApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public ToDoApi providesToDoApi() {

        OkHttpClient client = new OkHttpClient();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(END_POINT)
                .build();

        return restAdapter.create(ToDoApi.class);
    }

    @Provides
    @Singleton
    Context provideContext() {
        return app;
    }
}
