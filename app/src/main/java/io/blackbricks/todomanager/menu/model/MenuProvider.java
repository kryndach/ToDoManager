package io.blackbricks.todomanager.menu.model;


import java.util.LinkedList;
import java.util.List;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.menu.model.items.FilterMenuItem;
import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import rx.Observable;

/**
 * Created by yegorkryndach on 18/04/16.
 */
public class MenuProvider {

    public Observable<Menu> getMenu() {
        Menu menu = new Menu();

        List<FilterMenuItem> filterMenuItemList = new LinkedList<>();
        List<OptionalMenuItem> optionalMenuItemList = new LinkedList<>();
        List<GroupMenuItem> groupMenuItemList = new LinkedList<>();
        menu.setFilterMenuItemList(filterMenuItemList);
        menu.setOptionalMenuItemList(optionalMenuItemList);
        menu.setGroupMenuItemList(groupMenuItemList);

        filterMenuItemList.add(
                new FilterMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Inbox",
                        null,
                        new Filter(Filter.Type.INBOX)
                )
        );

        filterMenuItemList.add(
                new FilterMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Today",
                        null,
                        new Filter(Filter.Type.TODAY)
                )
        );

        filterMenuItemList.add(
                new FilterMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Tomorrow",
                        null,
                        new Filter(Filter.Type.TOMORROW)
                )
        );

        filterMenuItemList.add(
                new FilterMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Week",
                        null,
                        new Filter(Filter.Type.WEEK)
                )
        );

        filterMenuItemList.add(
                new FilterMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Overdue",
                        null,
                        new Filter(Filter.Type.OVERDUE)
                )
        );

        filterMenuItemList.add(
                new FilterMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Done",
                        null,
                        new Filter(Filter.Type.DONE)
                )
        );

        optionalMenuItemList.add(
                new OptionalMenuItem(
                        R.drawable.ic_assignment_turned_in_black_24dp,
                        "Support",
                        null,
                        OptionalMenuItem.Type.SUPPORT
                )
        );



        return Observable.just(menu);
    }

}
