package io.blackbricks.todomanager.dagger;

import dagger.Component;
import io.blackbricks.todomanager.database.DatabaseModule;

/**
 * Created by yegorkryndach on 15/04/16.
 */
@Component(
        modules = ToDoManagerModule.class
)
public interface ToDoManagerAppComponent {
}
