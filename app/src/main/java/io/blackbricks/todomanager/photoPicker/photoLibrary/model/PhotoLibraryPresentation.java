package io.blackbricks.todomanager.photoPicker.photoLibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 10/05/16.
 */

@ParcelablePlease
public class PhotoLibraryPresentation implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PhotoLibraryPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PhotoLibraryPresentation> CREATOR = new Creator<PhotoLibraryPresentation>() {
        public PhotoLibraryPresentation createFromParcel(Parcel source) {
            PhotoLibraryPresentation target = new PhotoLibraryPresentation();
            PhotoLibraryPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PhotoLibraryPresentation[] newArray(int size) {
            return new PhotoLibraryPresentation[size];
        }
    };
}
