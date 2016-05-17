package io.blackbricks.todomanager.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.background.Alarm;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.model.TaskProvider;

/**
 * Created by yegorkryndach on 15/04/16.
 */
@Component(
        modules = {ToDoManagerModule.class}
)
public interface ToDoManagerAppComponent {
}
