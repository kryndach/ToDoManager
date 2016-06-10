package io.blackbricks.todomanager.task;

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
public interface TaskComponent {
    TaskPresenter presenter();

    void inject(TaskFragment taskFragment);
}