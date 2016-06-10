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
import io.blackbricks.todomanager.utils.adapter.SectionedAdapter;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuAdapter extends SectionedAdapter implements MenuAdapterBinder {

    public interface FilterClickListener {
        void onFilterClicked(Filter filter);
    }

    public interface OptionalClickListener {
        void onOptionalClicked(OptionalMenuItem.Type type);
    }

    public interface GroupClickListener {
        void onGroupClicked(Group group);
    }

    public interface GroupLongClickListener {
        void onGroupLongClicked(Group group);
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
    public final int menuItemSeparator = ITEM_SECTION_HEADER;

    private Menu menu;
    private FilterClickListener filterClickListener;
    private OptionalClickListener optionalClickListener;
    private GroupClickListener groupClickListener;
    private GroupLongClickListener groupLongClickListener;

    public MenuAdapter(Context context, Menu menu, FilterClickListener filterClickListener,
                       OptionalClickListener optionalClickListener,
                       GroupClickListener groupClickListener,
                       GroupLongClickListener groupLongClickListener) {
        super(context);
        this.menu = menu;
        this.filterClickListener = filterClickListener;
        this.optionalClickListener = optionalClickListener;
        this.groupClickListener = groupClickListener;
        this.groupLongClickListener = groupLongClickListener;
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

    public void setGroupLongClickListener(GroupLongClickListener groupLongClickListener) {
        this.groupLongClickListener = groupLongClickListener;
    }

    @Override
    protected int getSectionItemCount(int section) {
        if (menu == null)
            return 0;
        switch (section) {
            case 0:
                return menu.getFilterMenuItemList().size();
            case 1:
                return menu.getOptionalMenuItemList().size();
            case 2:
                return menu.getGroupMenuItemList().size();
            default:
                return 0;
        }
    }

    @Override
    protected boolean supportHeader(int section) {
        return getSectionItemCount(section) != 0 && section != 0;
    }

    @Override
    protected int getSectionCount() {
        if (menu == null)
            return 0;
        return 3;
    }

    @Override
    protected int getItemViewTypeBySection(int section) {
        switch (section) {
            case 0:
                return menuItemFilter;
            case 1:
                return menuItemOptional;
            case 2:
                return menuItemGroup;
            default:
                return 0;
        }
    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemFilterViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemFilterViewHolder vh, int position) {
        int positionInSection = getPositionInSection(position);
        final FilterMenuItem filterMenuItem = menu.getFilterMenuItemList().get(positionInSection);
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
        int positionInSection = getPositionInSection(position);
        final GroupMenuItem groupMenuItem = menu.getGroupMenuItemList().get(positionInSection);
        vh.icon.setImageResource(groupMenuItem.getIconRes());
        vh.title.setText(groupMenuItem.getTitle());
        vh.description.setText(groupMenuItem.getDescription());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupClickListener.onGroupClicked(groupMenuItem.getGroup());
            }
        });
        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                groupLongClickListener.onGroupLongClicked(groupMenuItem.getGroup());
                return true;
            }
        });
        updateViewHolderSelection(vh, position);
    }

    @Override
    public void initViewHolder(MenuAdapterHolders.MenuItemOptionalViewHolder vh, View view, ViewGroup parent) {

    }

    @Override
    public void bindViewHolder(MenuAdapterHolders.MenuItemOptionalViewHolder vh, int position) {
        int positionInSection = getPositionInSection(position);
        final OptionalMenuItem optionalMenuItem = menu.getOptionalMenuItemList().get(positionInSection);
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

        int position = filterNumber + getPositionStartSection(0);
        selectItemNumber(position);
    }

    public void selectOptional(OptionalMenuItem.Type type) {
        int optionalNumber = 0;
        for(int i = 0; i < menu.getOptionalMenuItemList().size(); i++) {
            OptionalMenuItem optionalMenuItem = menu.getOptionalMenuItemList().get(i);
            if(optionalMenuItem.getType() == type) {
                optionalNumber = i;
                break;
            }
        }

        int position = optionalNumber + getPositionStartSection(1);
        selectItemNumber(position);
    }

    public void selectGroup(int groupId) {
        int groupNumber = 0;
        for(int i = 0; i < menu.getGroupMenuItemList().size(); i++) {
            GroupMenuItem groupMenuItem = menu.getGroupMenuItemList().get(i);
            if(groupMenuItem.getGroup().getGroupId() == groupId) {
                groupNumber = i;
                break;
            }
        }

        int position = groupNumber + getPositionStartSection(2);
        selectItemNumber(position);
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
