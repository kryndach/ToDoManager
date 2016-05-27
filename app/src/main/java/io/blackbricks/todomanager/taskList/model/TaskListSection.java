package io.blackbricks.todomanager.taskList.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 27/05/16.
 */
@ParcelablePlease
public class TaskListSection implements Parcelable {

    String title;
    ArrayList<Task> taskList;

    private TaskListSection() {
    }

    private TaskListSection(Builder builder) {
        title = builder.title;
        taskList = builder.taskList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

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

        public TaskListSection build() {
            return new TaskListSection(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TaskListSectionParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TaskListSection> CREATOR = new Creator<TaskListSection>() {
        public TaskListSection createFromParcel(Parcel source) {
            TaskListSection target = new TaskListSection();
            TaskListSectionParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TaskListSection[] newArray(int size) {
            return new TaskListSection[size];
        }
    };
}
