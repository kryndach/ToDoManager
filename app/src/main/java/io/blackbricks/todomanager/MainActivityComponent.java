package io.blackbricks.todomanager;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;

/**
 * Created by yegorkryndach on 15/04/16.
 */
@Singleton
@Component(
        modules = {ToDoManagerModule.class}
)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}