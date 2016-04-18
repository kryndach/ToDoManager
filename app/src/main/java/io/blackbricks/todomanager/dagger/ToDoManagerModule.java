package io.blackbricks.todomanager.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.blackbricks.todomanager.menu.model.MenuProvider;

/**
 * Created by yegorkryndach on 15/04/16.
 */

@Module
public class ToDoManagerModule {

    // Singletons
    private static MenuProvider menuProvider = new MenuProvider();

    @Singleton @Provides
    public MenuProvider providesMenuProvider() {
        return menuProvider;
    }

}
