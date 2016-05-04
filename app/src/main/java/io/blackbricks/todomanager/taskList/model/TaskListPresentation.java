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

    ArrayList<Task> taskList;

    private TaskListPresentation() {
    }

    private TaskListPresentation(Builder builder) {
        taskList = builder.taskList;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void insertTask(Task task) {
        taskList.add(task);
    }

    public Integer updateTask(Task task) {
        Integer index = null;
        int size = taskList.size();
        for (int i = 0; i < size; i++) {
            Task taskInList = taskList.get(i);
            if (taskInList.getId().equals(task.getId())) {
                index = i;
                break;
            }
        }

        if(index != null) {
            taskList.set(index, task);
        }

        return index;
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
        private ArrayList<Task> taskList;

        public Builder() {
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
