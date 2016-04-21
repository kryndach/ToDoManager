package io.blackbricks.todomanager.taskList.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ParcelablePlease
public class TaskListPresentation implements Parcelable {

    String title;
    ArrayList<Task> taskList;

    private TaskListPresentation() {
    }

    private TaskListPresentation(Builder builder) {
        title = builder.title;
        taskList = builder.taskList;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
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
        private String title;
        private ArrayList<Task> taskList;

        public Builder() {
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder taskList(ArrayList<Task> val) {
            taskList = val;
            return this;
        }

        public TaskListPresentation build() {
            return new TaskListPresentation(this);
        }
    }
}
