package io.blackbricks.todomanager.photoPicker.photoLibrary;

import android.widget.FrameLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.photoPicker.photoLibrary.model.PhotoLibraryPresentation;

/**
 * Created by yegorkryndach on 10/05/16.
 */
public class PhotoLibraryFragment extends BaseLceFragment<FrameLayout, PhotoLibraryPresentation, PhotoLibraryView,
        PhotoLibraryPresenter> implements PhotoLibraryView {

    private PhotoLibraryComponent photoLibraryComponent;
    private PhotoLibraryPresentation photoLibraryPresentation;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_photo_library;
    }

    @Override
    public LceViewState<PhotoLibraryPresentation, PhotoLibraryView> createViewState() {
        return new ParcelableDataLceViewState<>();
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
        return photoLibraryComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        photoLibraryComponent = DaggerPhotoLibraryComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .build();
        photoLibraryComponent.inject(this);
    }

    @Override
    public void setData(PhotoLibraryPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
