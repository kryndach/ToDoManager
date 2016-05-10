package io.blackbricks.todomanager.photoPicker;

import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerPresentation;
import io.blackbricks.todomanager.taskList.DaggerTaskListComponent;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoPickerFragment extends BaseLceFragment<FrameLayout, PhotoPickerPresentation,
        PhotoPickerView, PhotoPickerPresenter> implements PhotoPickerView {

    private PhotoPickerComponent photoPickerComponent;
    private PhotoPickerPresentation photoPickerPresentation;

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
        return null;
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
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .databaseModule(new DatabaseModule())
                .toDoManagerModule(new ToDoManagerModule(ToDoManagerApp.get(this.getActivity())))
                .build();
        photoPickerComponent.inject(this);
    }

    @Override
    public void setData(PhotoPickerPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    public void save() {

    }
}
