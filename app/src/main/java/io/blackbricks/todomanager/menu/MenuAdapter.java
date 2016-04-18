package io.blackbricks.todomanager.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.menu.model.Menu;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuAdapter extends SupportAnnotatedAdapter implements MenuAdapterBinder {

    private Menu menu;

    @ViewType(layout = R.layout.menu_item_filter,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.menu_item_filter_icon, name = "image", type = ImageView.class),
                    @ViewField(id = R.id.menu_item_filter_title, name = "title", type = TextView.class),
            })
    public final int menuItemFilter = 0;

    @ViewType(layout = R.layout.menu_item_group,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.menu_item_group_icon, name = "image", type = ImageView.class),
                    @ViewField(id = R.id.menu_item_group_title, name = "title", type = TextView.class),
                    @ViewField(id = R.id.menu_item_group_description, name = "description", type = TextView.class),
            })
    public final int menuItemGroup = 1;

    @ViewType(layout = R.layout.menu_item_optional,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.menu_item_optional_icon, name = "image", type = ImageView.class),
                    @ViewField(id = R.id.menu_item_optional_titile, name = "title", type = TextView.class),
            })
    public final int menuItemOptional = 2;

    @ViewType(layout = R.layout.menu_item_separator,
            initMethod = true,
            views = {})
    public final int menuItemSeparator = 3;

    public MenuAdapter(Context context) {
        super(context);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public int getItemCount() {
        if (menu == null)
            return 0;

        return 0;
    }


    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemFilterViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemFilterViewHolder vh, int position) {

    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemGroupViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemGroupViewHolder vh, int position) {

    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemOptionalViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemOptionalViewHolder vh, int position) {

    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemSeparatorViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemSeparatorViewHolder vh, int position) {

    }
}
