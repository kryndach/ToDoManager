package io.blackbricks.todomanager.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.annotatedadapter.annotation.ViewField;
import com.hannesdorfmann.annotatedadapter.annotation.ViewType;
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.menu.model.items.FilterMenuItem;
import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuAdapter extends SupportAnnotatedAdapter implements MenuAdapterBinder {

    public interface FilterClickListener {
        public void onFilterClicked(Filter filter);
    }

    public interface OptionalClickListener {
        public void onOptionalClicked(OptionalMenuItem.Type type);
    }

    public interface GroupClickListener {
        public void onGroupClicked(Group group);
    }

    @ViewType(layout = R.layout.list_filter_menu_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.menu_item_filter_icon, name = "icon", type = ImageView.class),
                    @ViewField(id = R.id.menu_item_filter_title, name = "title", type = TextView.class),
            })
    public final int menuItemFilter = 0;

    @ViewType(layout = R.layout.list_group_menu_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.menu_item_group_icon, name = "icon", type = ImageView.class),
                    @ViewField(id = R.id.menu_item_group_title, name = "title", type = TextView.class),
                    @ViewField(id = R.id.menu_item_group_description, name = "description", type = TextView.class),
            })
    public final int menuItemGroup = 1;

    @ViewType(layout = R.layout.list_optional_menu_item,
            initMethod = true,
            views = {
                    @ViewField(id = R.id.menu_item_optional_icon, name = "icon", type = ImageView.class),
                    @ViewField(id = R.id.menu_item_optional_titile, name = "title", type = TextView.class),
            })
    public final int menuItemOptional = 2;

    @ViewType(layout = R.layout.list_separator_menu_item,
            initMethod = true,
            views = {})
    public final int menuItemSeparator = 3;

    private Menu menu;
    private FilterClickListener filterClickListener;
    private OptionalClickListener optionalClickListener;
    private GroupClickListener groupClickListener;

    public MenuAdapter(Context context, Menu menu, FilterClickListener filterClickListener,
                       OptionalClickListener optionalClickListener,
                       GroupClickListener groupClickListener) {
        super(context);
        this.menu = menu;
        this.filterClickListener = filterClickListener;
        this.optionalClickListener = optionalClickListener;
        this.groupClickListener = groupClickListener;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setFilterClickListener(FilterClickListener filterClickListener) {
        this.filterClickListener = filterClickListener;
    }

    public void setOptionalClickListener(OptionalClickListener optionalClickListener) {
        this.optionalClickListener = optionalClickListener;
    }

    public void setGroupClickListener(GroupClickListener groupClickListener) {
        this.groupClickListener = groupClickListener;
    }

    @Override
    public int getItemCount() {
        if (menu == null)
            return 0;

        int itemCount = 0;
        // add filters count
        itemCount += menu.getFilterMenuItemList().size();
        // Add 1 for separator between filter and optional section
        itemCount++;
        // add optionals count
        itemCount += menu.getOptionalMenuItemList().size();
        // Add 1 for separator between optional and group section
        itemCount++;
        // add groups count
        itemCount += menu.getGroupMenuItemList().size();

        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (menu == null)
            return 0;

        int itemCount = 0;
        // add filters count
        itemCount += menu.getFilterMenuItemList().size();
        if(position < itemCount)
            return menuItemFilter;
        // Add 1 for separator between filter and optional section
        itemCount++;
        if(position < itemCount)
            return menuItemSeparator;
        // add optionals count
        itemCount += menu.getOptionalMenuItemList().size();
        if(position < itemCount)
            return menuItemOptional;
        // Add 1 for separator between optional and group section
        itemCount++;
        if(position < itemCount)
            return menuItemSeparator;
        // add groups count
        itemCount += menu.getGroupMenuItemList().size();
        if(position < itemCount)
            return menuItemGroup;

        return 0;
    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemFilterViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemFilterViewHolder vh, int position) {
        final FilterMenuItem filterMenuItem = menu.getFilterMenuItemList().get(position);
        vh.icon.setImageResource(filterMenuItem.getIconRes());
        vh.title.setText(filterMenuItem.getTitle());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterClickListener.onFilterClicked(filterMenuItem.getFilter());
            }
        });
        updateViewHolderSelection(vh, position);
    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemGroupViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemGroupViewHolder vh, int position) {
        int positionInItemList = position - menu.getFilterMenuItemList().size() - 1
                - menu.getOptionalMenuItemList().size() - 1;
        final GroupMenuItem groupMenuItem = menu.getGroupMenuItemList().get(positionInItemList);
        vh.icon.setImageResource(groupMenuItem.getIconRes());
        vh.title.setText(groupMenuItem.getTitle());
        vh.description.setText(groupMenuItem.getDescription());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupClickListener.onGroupClicked(groupMenuItem.getGroup());
            }
        });
        updateViewHolderSelection(vh, position);
    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemOptionalViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemOptionalViewHolder vh, int position) {
        int positionInItemList = position - menu.getFilterMenuItemList().size() - 1;
        final OptionalMenuItem optionalMenuItem = menu.getOptionalMenuItemList().get(positionInItemList);
        vh.icon.setImageResource(optionalMenuItem.getIconRes());
        vh.title.setText(optionalMenuItem.getTitle());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionalClickListener.onOptionalClicked(optionalMenuItem.getType());
            }
        });
        updateViewHolderSelection(vh, position);
    }

    private void updateViewHolderSelection(RecyclerView.ViewHolder vh, int position) {
        if(menu.getFocusedItem() != null && menu.getFocusedItem() == position) {
            vh.itemView.setSelected(true);
        } else {
            vh.itemView.setSelected(false);
        }
    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemSeparatorViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemSeparatorViewHolder vh, int position) {

    }

    // selection methods
    public void selectFilterType(Filter.Type type) {
        int filterNumber = 0;
        for(int i = 0; i < menu.getFilterMenuItemList().size(); i++) {
            FilterMenuItem filterMenuItem = menu.getFilterMenuItemList().get(i);
            if(filterMenuItem.getFilter().getType() == type) {
                filterNumber = i;
                break;
            }
        }

        selectItemNumber(filterNumber);
    }

    public void selectOptional(OptionalMenuItem.Type type) {
        int itemNumber = 0;
        // add filters count
        itemNumber += menu.getFilterMenuItemList().size();
        // Add 1 for separator between filter and optional section
        itemNumber++;

        int optionalNumber = 0;
        for(int i = 0; i < menu.getOptionalMenuItemList().size(); i++) {
            OptionalMenuItem optionalMenuItem = menu.getOptionalMenuItemList().get(i);
            if(optionalMenuItem.getType() == type) {
                optionalNumber = i;
                break;
            }
        }

        itemNumber += optionalNumber;
        selectItemNumber(itemNumber);
    }

    public void selectGroup(int groupId) {
        int itemNumber = 0;
        // add filters count
        itemNumber += menu.getFilterMenuItemList().size();
        // Add 1 for separator between filter and optional section
        itemNumber++;
        // add optionals count
        itemNumber += menu.getOptionalMenuItemList().size();
        // Add 1 for separator between optional and group section
        itemNumber++;

        int groupNumber = 0;
        for(int i = 0; i < menu.getGroupMenuItemList().size(); i++) {
            GroupMenuItem groupMenuItem = menu.getGroupMenuItemList().get(i);
            if(groupMenuItem.getGroup().getId() == groupId) {
                groupNumber = i;
                break;
            }
        }

        itemNumber += groupNumber;
        selectItemNumber(itemNumber);
    }

    private void selectItemNumber(int itemNumber) {
        Integer oldSelectedItemNumber = menu.getFocusedItem();

        menu.setFocusedItem(itemNumber);
        if(oldSelectedItemNumber != null) {
            notifyItemChanged(oldSelectedItemNumber);
        }
        notifyItemChanged(itemNumber);
    }
}
