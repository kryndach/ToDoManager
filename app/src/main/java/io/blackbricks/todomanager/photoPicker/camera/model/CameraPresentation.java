package io.blackbricks.todomanager.photoPicker.camera.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 10/05/16.
 */

@ParcelablePlease
public class CameraPresentation implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        CameraPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<CameraPresentation> CREATOR = new Creator<CameraPresentation>() {
        public CameraPresentation createFromParcel(Parcel source) {
            CameraPresentation target = new CameraPresentation();
            CameraPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public CameraPresentation[] newArray(int size) {
            return new CameraPresentation[size];
        }
    };
}
