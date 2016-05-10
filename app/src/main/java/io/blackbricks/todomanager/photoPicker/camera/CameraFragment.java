package io.blackbricks.todomanager.photoPicker.camera;

import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;

import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.photoPicker.camera.model.CameraPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class CameraFragment extends BaseLceFragment<FrameLayout, CameraPresentation, CameraView, CameraPresenter>
        implements CameraView {

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<CameraPresentation, CameraView> createViewState() {
        return null;
    }

    @Override
    public CameraPresentation getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public CameraPresenter createPresenter() {
        return null;
    }

    @Override
    public void setData(CameraPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
