package io.blackbricks.todomanager.photoPicker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import io.blackbricks.todomanager.photoPicker.camera.CameraFragment;
import io.blackbricks.todomanager.photoPicker.model.PhotoPickerScreen;
import io.blackbricks.todomanager.photoPicker.photoLibrary.PhotoLibraryFragment;

/**
 * Created by yegorkryndach on 11/05/16.
 */
public class PhotoPickerAdapter extends FragmentPagerAdapter {

    private List<PhotoPickerScreen> screens;

    public PhotoPickerAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<PhotoPickerScreen> getScreens() {
        return screens;
    }

    public void setScreens(List<PhotoPickerScreen> screens) {
        this.screens = screens;
    }

    @Override public Fragment getItem(int position) {

        PhotoPickerScreen screen = screens.get(position);
        if (screen.getType() == PhotoPickerScreen.Type.CAMERA) {
            return new CameraFragment();
        }
        if (screen.getType() == PhotoPickerScreen.Type.LIBRARY) {
            return new PhotoLibraryFragment();
        }

        throw new RuntimeException("Unknown Profile Screen (no fragment associated with this type");
    }

    @Override public int getCount() {

        return screens == null ? 0 : screens.size();
    }

    @Override public CharSequence getPageTitle(int position) {
        return screens.get(position).getType().toString();
    }
}
