package io.blackbricks.todomanager.photoPicker.photoLibrary;

import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;

import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.photoPicker.photoLibrary.model.PhotoLibraryPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoLibraryFragment extends BaseLceFragment<FrameLayout, PhotoLibraryPresentation, PhotoLibraryView,
        PhotoLibraryPresenter> implements PhotoLibraryView {

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<PhotoLibraryPresentation, PhotoLibraryView> createViewState() {
        return null;
    }

    @Override
    public PhotoLibraryPresentation getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public PhotoLibraryPresenter createPresenter() {
        return null;
    }

    @Override
    public void setData(PhotoLibraryPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
