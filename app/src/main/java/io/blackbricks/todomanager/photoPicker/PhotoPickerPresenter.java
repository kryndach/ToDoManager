package io.blackbricks.todomanager.photoPicker;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoPickerPresenter extends BaseRxLcePresenter<PhotoPickerView, PhotoPickerPresentation> {

    @Inject
    public PhotoPickerPresenter() {
    }
}
