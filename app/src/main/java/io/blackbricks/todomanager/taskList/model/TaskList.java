package io.blackbricks.todomanager.taskList.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ParcelablePlease
public class TaskList implements Parcelable {



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TaskListParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TaskList> CREATOR = new Creator<TaskList>() {
        public TaskList createFromParcel(Parcel source) {
            TaskList target = new TaskList();
            TaskListParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TaskList[] newArray(int size) {
            return new TaskList[size];
        }
    };
}
