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

    public GroupMenuItem(int iconRes, String title, String description, Group group) {
        super(iconRes, title, description);
        this.group = group;
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
}
