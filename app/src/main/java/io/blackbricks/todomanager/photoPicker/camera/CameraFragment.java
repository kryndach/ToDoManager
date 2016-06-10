package io.blackbricks.todomanager.photoPicker.camera;

import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.photoPicker.camera.model.CameraPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class CameraFragment extends BaseLceFragment<FrameLayout, CameraPresentation, CameraView, CameraPresenter>
        implements CameraView {

    private CameraComponent cameraComponent;
    private CameraPresentation cameraPresentation;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_camera;
    }

    @Override
    public LceViewState<CameraPresentation, CameraView> createViewState() {
        return new ParcelableDataLceViewState<>();
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
        return cameraComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        cameraComponent = DaggerCameraComponent.builder()
                .appComponent(ToDoManagerApp.getAppComponent())
                .build();
        cameraComponent.inject(this);
    }

    @Override
    public void setData(CameraPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
