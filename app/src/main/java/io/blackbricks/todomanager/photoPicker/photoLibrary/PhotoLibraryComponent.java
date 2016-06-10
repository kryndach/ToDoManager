package io.blackbricks.todomanager.photoPicker.photoLibrary;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.AppComponent;

/**
 * Created by yegorkryndach on 10/05/16.
 */
@ActivityScope
@Component(
        dependencies = AppComponent.class
)
public interface PhotoLibraryComponent {
    PhotoLibraryPresenter presenter();

    void inject(PhotoLibraryFragment photoLibraryFragment);
}
