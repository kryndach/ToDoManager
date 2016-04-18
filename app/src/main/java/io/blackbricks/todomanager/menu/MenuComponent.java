package io.blackbricks.todomanager.menu;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.NavigationModule;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@Singleton
@Component(
        modules = {NavigationModule.class},
        dependencies = ToDoManagerAppComponent.class
)
public interface MenuComponent {
    MenuPresenter presenter();

    void inject(MenuFragment menuFragment);
}