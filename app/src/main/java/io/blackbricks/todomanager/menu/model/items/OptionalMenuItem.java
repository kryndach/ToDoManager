package io.blackbricks.todomanager.menu.model.items;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by yegorkryndach on 17/04/16.
 */
@ParcelablePlease
public class OptionalMenuItem extends MenuItem implements Parcelable {

    public enum Type {
        SUPPORT
    }

    Type type;

    private OptionalMenuItem() {
    }

    public OptionalMenuItem(int iconRes, String title, String description, Type type) {
        super(iconRes, title, description);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        OptionalMenuItemParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<OptionalMenuItem> CREATOR = new Creator<OptionalMenuItem>() {
        public OptionalMenuItem createFromParcel(Parcel source) {
            OptionalMenuItem target = new OptionalMenuItem();
            OptionalMenuItemParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public OptionalMenuItem[] newArray(int size) {
            return new OptionalMenuItem[size];
        }
    };
}
