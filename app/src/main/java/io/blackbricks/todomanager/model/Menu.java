package io.blackbricks.todomanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.List;

import io.blackbricks.todomanager.model.menu.FilterMenuItem;
import io.blackbricks.todomanager.model.menu.GroupMenuItem;
import io.blackbricks.todomanager.model.menu.OptionalMenuItem;

/**
 * Created by yegorkryndach on 16/04/16.
 */
@ParcelablePlease
public class Menu implements Parcelable {

    List<FilterMenuItem> filterMenuItemList;
    List<OptionalMenuItem> optionalMenuItemList;
    List<GroupMenuItem> groupMenuItemList;

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
