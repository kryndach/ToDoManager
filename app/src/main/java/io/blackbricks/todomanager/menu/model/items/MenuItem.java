package io.blackbricks.todomanager.menu.model.items;

import android.os.Parcelable;

/**
 * Created by yegorkryndach on 17/04/16.
 */
public abstract class MenuItem implements Parcelable {
    int iconRes;
    String title;
    String description;

    protected MenuItem() {
    }

    protected MenuItem(int iconRes, String title, String description) {
        this.iconRes = iconRes;
        this.title = title;
        this.description = description;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
