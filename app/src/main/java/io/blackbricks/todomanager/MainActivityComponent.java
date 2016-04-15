package io.blackbricks.todomanager;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.NavigationModule;

/**
 * Created by yegorkryndach on 15/04/16.
 */
@Singleton
@Component(
        modules = NavigationModule.class)
public interface MainActivityComponent {
    public void inject(MainActivity activity);
}