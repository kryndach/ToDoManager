package io.blackbricks.todomanager.task.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.opengl.Visibility;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

import io.blackbricks.todomanager.model.Attachment;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;

/**
 * Created by yegorkryndach on 19/04/16.
 */
@ParcelablePlease
public class TaskPresentation extends BaseObservable implements Parcelable {

    Task task;
    Group group;
    ArrayList<AttachmentPresentation> attachmentPresentations;
    ArrayList<AttachmentPresentation> addedAttachmentPresentations;
    ArrayList<AttachmentPresentation> removedAttachmentPresentations;
    ArrayList<Group> groupList;
    ArrayList<Integer> iconList;

    private TaskPresentation() {
    }

    private TaskPresentation(Builder builder) {
        task = builder.task;
        setGroup(builder.group);
        attachmentPresentations = builder.attachmentPresentations;
        addedAttachmentPresentations = builder.addedAttachmentPresentations;
        removedAttachmentPresentations = builder.removedAttachmentPresentations;
        groupList = builder.groupList;
        iconList = builder.iconList;
    }

    @Bindable
    public Task getTask() {
        return task;
    }

    @Bindable
    public Group getGroup() {
        return group;
    }

    public ArrayList<AttachmentPresentation> getAttachmentPresentations() {
        return attachmentPresentations;
    }

    public ArrayList<AttachmentPresentation> getAddedAttachmentPresentations() {
        return addedAttachmentPresentations;
    }

    public ArrayList<AttachmentPresentation> getRemovedAttachmentPresentations() {
        return removedAttachmentPresentations;
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    public ArrayList<Integer> getIconList() {
        return iconList;
    }

    public void setGroup(Group group) {
        this.group = group;
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
        private ArrayList<AttachmentPresentation> addedAttachmentPresentations;
        private ArrayList<AttachmentPresentation> removedAttachmentPresentations;
        private ArrayList<Group> groupList;
        private ArrayList<Integer> iconList;

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

        public Builder addedAttachmentPresentations(ArrayList<AttachmentPresentation> val) {
            addedAttachmentPresentations = val;
            return this;
        }

        public Builder removedAttachmentPresentations(ArrayList<AttachmentPresentation> val) {
            removedAttachmentPresentations = val;
            return this;
        }

        public Builder groupList(ArrayList<Group> val) {
            groupList = val;
            return this;
        }

        public Builder iconList(ArrayList<Integer> val) {
            iconList = val;
            return this;
        }

        public TaskPresentation build() {
            return new TaskPresentation(this);
        }
    }
}
