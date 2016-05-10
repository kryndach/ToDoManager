package io.blackbricks.todomanager.photoPicker;

import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;

import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoPickerFragment extends BaseLceFragment<FrameLayout, PhotoPickerPresentation,
        PhotoPickerView, PhotoPickerPresenter> implements PhotoPickerView {

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<PhotoPickerPresentation, PhotoPickerView> createViewState() {
        return null;
    }

    @Override
    public PhotoPickerPresentation getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public PhotoPickerPresenter createPresenter() {
        return null;
    }

    @Override
    public void setData(PhotoPickerPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
