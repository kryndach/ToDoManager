package io.blackbricks.todomanager.menu;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.AppComponent;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class
)
public interface MenuComponent {
    MenuPresenter presenter();

    void inject(MenuFragment menuFragment);
}