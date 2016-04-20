package io.blackbricks.todomanager.menu.model.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 17/04/16.
 */
@ParcelablePlease
public class GroupMenuItem extends MenuItem implements Parcelable {

    Group group;

    private GroupMenuItem() {
    }

    private GroupMenuItem(Builder builder) {
        iconRes = builder.iconRes;
        title = builder.title;
        description = builder.description;
        group = builder.group;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        GroupMenuItemParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<GroupMenuItem> CREATOR = new Creator<GroupMenuItem>() {
        public GroupMenuItem createFromParcel(Parcel source) {
            GroupMenuItem target = new GroupMenuItem();
            GroupMenuItemParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public GroupMenuItem[] newArray(int size) {
            return new GroupMenuItem[size];
        }
    };

    public static final class Builder {
        private int iconRes;
        private String title;
        private String description;
        private Group group;

        public Builder() {
        }

        public Builder iconRes(int val) {
            iconRes = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder group(Group val) {
            group = val;
            return this;
        }

        public GroupMenuItem build() {
            return new GroupMenuItem(this);
        }
    }
}
