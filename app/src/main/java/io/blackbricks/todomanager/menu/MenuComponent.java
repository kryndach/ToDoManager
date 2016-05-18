package io.blackbricks.todomanager.menu;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@ActivityScope
@Component(
        dependencies = ToDoManagerAppComponent.class
)
public interface MenuComponent {
    MenuPresenter presenter();

    void inject(MenuFragment menuFragment);
}