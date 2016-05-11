package io.blackbricks.todomanager.photoPicker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 11/05/16.
 */

@ParcelablePlease
public class PhotoPickerScreen implements Parcelable {

    public enum Type {
        CAMERA("Camera"),
        LIBRARY("Library");

        private final String text;

        Type(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    Type type;

    private PhotoPickerScreen() {
    }

    public PhotoPickerScreen(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PhotoPickerScreenParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PhotoPickerScreen> CREATOR = new Creator<PhotoPickerScreen>() {
        public PhotoPickerScreen createFromParcel(Parcel source) {
            PhotoPickerScreen target = new PhotoPickerScreen();
            PhotoPickerScreenParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PhotoPickerScreen[] newArray(int size) {
            return new PhotoPickerScreen[size];
        }
    };
}
