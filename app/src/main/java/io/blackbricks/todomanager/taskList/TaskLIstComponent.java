package io.blackbricks.todomanager.taskList;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.background.Alarm;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ActivityScope
@Component(
        dependencies = ToDoManagerAppComponent.class
)
public interface TaskListComponent {
    TaskListPresenter presenter();

    void inject(TaskListFragment taskListFragment);
    void inject(TaskListActivity taskListActivity);
}