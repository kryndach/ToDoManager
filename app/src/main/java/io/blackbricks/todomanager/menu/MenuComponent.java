package io.blackbricks.todomanager.menu;

import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.menu.model.MenuProvider;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@Singleton
@Component(
        modules = {ToDoManagerModule.class, DatabaseModule.class},
        dependencies = ToDoManagerAppComponent.class
)
public interface MenuComponent {
    MenuPresenter presenter();

    void inject(MenuFragment menuFragment);
}