package io.blackbricks.todomanager.photoPicker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.List;

/**
 * Created by yegorkryndach on 10/05/16.
 */

@ParcelablePlease
public class PhotoPickerPresentation implements Parcelable {

    List<PhotoPickerScreen> screens;

    private PhotoPickerPresentation() {
    }

    public PhotoPickerPresentation(List<PhotoPickerScreen> screens) {
        this.screens = screens;
    }

    public List<PhotoPickerScreen> getScreens() {
        return screens;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PhotoPickerPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PhotoPickerPresentation> CREATOR = new Creator<PhotoPickerPresentation>() {
        public PhotoPickerPresentation createFromParcel(Parcel source) {
            PhotoPickerPresentation target = new PhotoPickerPresentation();
            PhotoPickerPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PhotoPickerPresentation[] newArray(int size) {
            return new PhotoPickerPresentation[size];
        }
    };
}
