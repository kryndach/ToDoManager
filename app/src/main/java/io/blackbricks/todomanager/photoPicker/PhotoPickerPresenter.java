package io.blackbricks.todomanager.photoPicker;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerPresentation;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerProvider;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoPickerPresenter extends BaseRxLcePresenter<PhotoPickerView, PhotoPickerPresentation> {

    @Inject
    PhotoPickerProvider photoPickerProvider;

    @Inject
    public PhotoPickerPresenter() {
    }

    public void loadPhotoPicker(boolean pullToRefresh) {
        subscribe(photoPickerProvider.getPhotoPickerPresentation(), pullToRefresh);
    }
}
