package io.blackbricks.todomanager.taskList;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.AppComponent;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class
)
public interface TaskListComponent {
    TaskListPresenter presenter();

    void inject(TaskListFragment taskListFragment);
    void inject(TaskListActivity taskListActivity);
}