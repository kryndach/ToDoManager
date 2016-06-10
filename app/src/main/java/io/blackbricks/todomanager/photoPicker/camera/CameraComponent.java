package io.blackbricks.todomanager.photoPicker.camera;

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
public interface CameraComponent {
    CameraPresenter presenter();

    void inject(CameraFragment cameraFragment);
}
