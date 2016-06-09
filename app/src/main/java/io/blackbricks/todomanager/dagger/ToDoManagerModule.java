package io.blackbricks.todomanager.dagger;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.blackbricks.todomanager.ToDoManagerApp;

/**
 * Created by yegorkryndach on 15/04/16.
 */

@Module
public class ToDoManagerModule {

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
    Context provideContext() {
        return app;
    }
}
