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

    private OptionalMenuItem(Builder builder) {
        iconRes = builder.iconRes;
        title = builder.title;
        description = builder.description;
        type = builder.type;
    }

    public Type getType() {
        return type;
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

    public static final class Builder {
        private int iconRes;
        private String title;
        private String description;
        private Type type;

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

        public Builder type(Type val) {
            type = val;
            return this;
        }

        public OptionalMenuItem build() {
            return new OptionalMenuItem(this);
        }
    }
}
