package io.blackbricks.todomanager.task.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ParcelablePlease
public class TaskPresentation implements Parcelable {



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TaskPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TaskPresentation> CREATOR = new Creator<TaskPresentation>() {
        public TaskPresentation createFromParcel(Parcel source) {
            TaskPresentation target = new TaskPresentation();
            TaskPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TaskPresentation[] newArray(int size) {
            return new TaskPresentation[size];
        }
    };
}
