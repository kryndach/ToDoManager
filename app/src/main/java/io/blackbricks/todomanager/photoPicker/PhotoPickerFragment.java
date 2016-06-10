package io.blackbricks.todomanager.photoPicker;

import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoPickerFragment extends BaseLceFragment<FrameLayout, PhotoPickerPresentation,
        PhotoPickerView, PhotoPickerPresenter> implements PhotoPickerView {

    private PhotoPickerComponent photoPickerComponent;
    private PhotoPickerPresentation photoPickerPresentation;
    private PhotoPickerAdapter adapter;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_photo_picker;
    }

    @Override
    public LceViewState<PhotoPickerPresentation, PhotoPickerView> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Override
    public PhotoPickerPresentation getData() {
        return photoPickerPresentation;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public PhotoPickerPresenter createPresenter() {
        return photoPickerComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        photoPickerComponent = DaggerPhotoPickerComponent.builder()
                .appComponent(ToDoManagerApp.getAppComponent())
                .build();
        photoPickerComponent.inject(this);
    }

    @Override
    public void setData(PhotoPickerPresentation data) {
        this.photoPickerPresentation = data;
        adapter = new PhotoPickerAdapter(getFragmentManager());
        adapter.setScreens(data.getScreens());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadPhotoPicker(pullToRefresh);
    }

    public void save() {

    }
}
