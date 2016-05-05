package io.blackbricks.todomanager.menu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;
import java.util.List;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.menu.model.items.FilterMenuItem;
import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@ParcelablePlease
public class Menu implements Parcelable {

    ArrayList<FilterMenuItem> filterMenuItemList;
    ArrayList<OptionalMenuItem> optionalMenuItemList;
    ArrayList<GroupMenuItem> groupMenuItemList;

    private Menu() {
    }

    private Menu(Builder builder) {
        filterMenuItemList = builder.filterMenuItemList;
        optionalMenuItemList = builder.optionalMenuItemList;
        groupMenuItemList = builder.groupMenuItemList;
    }

    public ArrayList<FilterMenuItem> getFilterMenuItemList() {
        return filterMenuItemList;
    }

    public ArrayList<OptionalMenuItem> getOptionalMenuItemList() {
        return optionalMenuItemList;
    }

    public ArrayList<GroupMenuItem> getGroupMenuItemList() {
        return groupMenuItemList;
    }

    public static GroupMenuItem getGroupMenuItem(Group group) {
        String description = "Tasks " + group.getTaskCount();
        if (group.getHotTaskCount() > 0) {
            description = "Hot " + group.getHotTaskCount() + ", " + description;
        }

        return new GroupMenuItem.Builder()
                .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                .title(group.getName())
                .description(description)
                .group(group)
                .build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        MenuParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        public Menu createFromParcel(Parcel source) {
            Menu target = new Menu();
            MenuParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public static final class Builder {
        private ArrayList<FilterMenuItem> filterMenuItemList;
        private ArrayList<OptionalMenuItem> optionalMenuItemList;
        private ArrayList<GroupMenuItem> groupMenuItemList;

        public Builder() {
        }

        public Builder filterMenuItemList(ArrayList<FilterMenuItem> val) {
            filterMenuItemList = val;
            return this;
        }

        public Builder optionalMenuItemList(ArrayList<OptionalMenuItem> val) {
            optionalMenuItemList = val;
            return this;
        }

        public Builder groupMenuItemList(ArrayList<GroupMenuItem> val) {
            groupMenuItemList = val;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
