package io.blackbricks.todomanager.taskList.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.Pair;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ParcelablePlease
public class TaskListPresentation implements Parcelable {

    ArrayList<Pair<String, ArrayList<Task>>> sectionList;

    private TaskListPresentation() {
    }

    private TaskListPresentation(Builder builder) {
        sectionList = builder.sectionList;
    }

    public ArrayList<Pair<String, ArrayList<Task>>> getSectionList() {
        return sectionList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TaskListPresentationParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TaskListPresentation> CREATOR = new Creator<TaskListPresentation>() {
        public TaskListPresentation createFromParcel(Parcel source) {
            TaskListPresentation target = new TaskListPresentation();
            TaskListPresentationParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TaskListPresentation[] newArray(int size) {
            return new TaskListPresentation[size];
        }
    };

    public static final class Builder {
        private ArrayList<Pair<String, ArrayList<Task>>> sectionList;

        public Builder() {
        }

        public TaskListPresentation build() {
            return new TaskListPresentation(this);
        }

        public Builder sectionList(ArrayList<Pair<String, ArrayList<Task>>> val) {
            sectionList = val;
            return this;
        }
    }
}
