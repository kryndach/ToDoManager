package io.blackbricks.todomanager.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.blackbricks.todomanager.IntentStarter;

/**
 * Created by yegorkryndach on 14/04/16.
 */

@Module
public class NavigationModule {

    @Singleton
    @Provides
    public IntentStarter providesIntentStarter() {
        return new IntentStarter();
    }
}