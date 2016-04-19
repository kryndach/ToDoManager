package io.blackbricks.todomanager.taskList;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.NavigationModule;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@Singleton
@Component(
        modules = {ToDoManagerModule.class, NavigationModule.class},
        dependencies = ToDoManagerAppComponent.class
)
public interface TaskListComponent {
    TaskListPresenter presenter();

    void inject(TaskListFragment taskListFragment);
}