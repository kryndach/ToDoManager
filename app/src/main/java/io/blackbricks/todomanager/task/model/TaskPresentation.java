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
    ArrayList<AttachmentPresentation> attachmentPresentations;
    ArrayList<AttachmentPresentation> removedAttachmentPresentations;

    private TaskPresentation() {
    }

    private TaskPresentation(Builder builder) {
        task = builder.task;
        group = builder.group;
        attachmentPresentations = builder.attachmentPresentations;
        removedAttachmentPresentations = builder.removedAttachmentPresentations;
    }

    public Task getTask() {
        return task;
    }

    public Group getGroup() {
        return group;
    }

    public ArrayList<AttachmentPresentation> getAttachmentPresentations() {
        return attachmentPresentations;
    }

    public ArrayList<AttachmentPresentation> getRemovedAttachmentPresentations() {
        return removedAttachmentPresentations;
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
        private ArrayList<AttachmentPresentation> attachmentPresentations;
        private ArrayList<AttachmentPresentation> removedAttachmentPresentations;

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

        public Builder attachmentPresentations(ArrayList<AttachmentPresentation> val) {
            attachmentPresentations = val;
            return this;
        }

        public Builder removedAttachmentPresentations(ArrayList<AttachmentPresentation> val) {
            removedAttachmentPresentations = val;
            return this;
        }

        public TaskPresentation build() {
            return new TaskPresentation(this);
        }
    }
}
