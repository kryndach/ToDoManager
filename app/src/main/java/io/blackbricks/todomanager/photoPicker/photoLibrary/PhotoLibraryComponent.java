package io.blackbricks.todomanager.photoPicker.photoLibrary;

import javax.inject.Singleton;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;

/**
 * Created by yegorkryndach on 10/05/16.
 */
@ActivityScope
@Component(
        dependencies = ToDoManagerAppComponent.class
)
public interface PhotoLibraryComponent {
    PhotoLibraryPresenter presenter();

    void inject(PhotoLibraryFragment photoLibraryFragment);
}
