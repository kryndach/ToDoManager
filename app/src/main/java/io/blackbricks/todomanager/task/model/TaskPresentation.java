package io.blackbricks.todomanager.task.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ParcelablePlease
public class TaskPresentation implements Parcelable {

    Task task;
    Group group;
    ArrayList<Attachment> attachments;
    ArrayList<Attachment> removedAttachments;

    private TaskPresentation() {
    }

    private TaskPresentation(Builder builder) {
        task = builder.task;
        group = builder.group;
        attachments = builder.attachments;
        removedAttachments = builder.removedAttachments;
    }

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

    public static final class Builder {
        private Task task;
        private Group group;
        private ArrayList<Attachment> attachments;
        private ArrayList<Attachment> removedAttachments;

        public Builder() {
        }

        public Builder task(Task val) {
            task = val;
            return this;
        }

        public Builder group(Group val) {
            group = val;
            return this;
        }

        public Builder attachments(ArrayList<Attachment> val) {
            attachments = val;
            return this;
        }

        public Builder removedAttachments(ArrayList<Attachment> val) {
            removedAttachments = val;
            return this;
        }

        public TaskPresentation build() {
            return new TaskPresentation(this);
        }
    }
}
