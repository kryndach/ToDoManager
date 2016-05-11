package io.blackbricks.todomanager.photoPicker.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.R;
import rx.Observable;

/**
 * Created by yegorkryndach on 10/05/16.
 */

@Singleton
public class PhotoPickerProvider {

    @Inject
    public PhotoPickerProvider() {
    }

    public Observable<PhotoPickerPresentation> getPhotoPickerPresentation(){
        ArrayList<PhotoPickerScreen> screens = new ArrayList<>();
        screens.add(new PhotoPickerScreen(PhotoPickerScreen.Type.LIBRARY));
        screens.add(new PhotoPickerScreen(PhotoPickerScreen.Type.CAMERA));
        return Observable.just(new PhotoPickerPresentation(screens));
    }
}
