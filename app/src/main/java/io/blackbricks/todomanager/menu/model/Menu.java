package io.blackbricks.todomanager.menu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.List;

import io.blackbricks.todomanager.menu.model.items.FilterMenuItem;
import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@ParcelablePlease
public class Menu implements Parcelable {

    List<FilterMenuItem> filterMenuItemList;
    List<OptionalMenuItem> optionalMenuItemList;
    List<GroupMenuItem> groupMenuItemList;

    public List<FilterMenuItem> getFilterMenuItemList() {
        return filterMenuItemList;
    }

    public void setFilterMenuItemList(List<FilterMenuItem> filterMenuItemList) {
        this.filterMenuItemList = filterMenuItemList;
    }

    public List<OptionalMenuItem> getOptionalMenuItemList() {
        return optionalMenuItemList;
    }

    public void setOptionalMenuItemList(List<OptionalMenuItem> optionalMenuItemList) {
        this.optionalMenuItemList = optionalMenuItemList;
    }

    public List<GroupMenuItem> getGroupMenuItemList() {
        return groupMenuItemList;
    }

    public void setGroupMenuItemList(List<GroupMenuItem> groupMenuItemList) {
        this.groupMenuItemList = groupMenuItemList;
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
}
