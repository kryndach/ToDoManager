package io.blackbricks.todomanager.menu.model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.menu.model.items.FilterMenuItem;
import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.GroupProvider;
import rx.Observable;

/**
 * Created by yegorkryndach on 18/04/16.
 */
@Singleton
public class MenuProvider {

    @Inject
    GroupProvider groupProvider;

    @Inject
    public MenuProvider() {
    }

    public Observable<Menu> getMenu() {

        List<FilterMenuItem> filterMenuItemList = getFilterMenuItems();

        List<OptionalMenuItem> optionalMenuItemList = getOptionalMenuItems();

        List<GroupMenuItem> groupMenuItemList = new ArrayList<>();

        Menu menu = new Menu.Builder()
                .filterMenuItemList(filterMenuItemList)
                .groupMenuItemList(groupMenuItemList)
                .optionalMenuItemList(optionalMenuItemList)
                .build();

        return Observable.just(menu);
    }

    @NonNull
    private List<OptionalMenuItem> getOptionalMenuItems() {
        List<OptionalMenuItem> optionalMenuItemList = new ArrayList<>();

        optionalMenuItemList.add(
                new OptionalMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Inbox")
                        .description(null)
                        .type(OptionalMenuItem.Type.SUPPORT)
                        .build()
        );
        return optionalMenuItemList;
    }

    @NonNull
    private List<FilterMenuItem> getFilterMenuItems() {
        List<FilterMenuItem> filterMenuItemList = new ArrayList<>();

        filterMenuItemList.add(
                new FilterMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Inbox")
                        .description(null)
                        .filter(new Filter(Filter.Type.INBOX))
                        .build()
        );

        filterMenuItemList.add(
                new FilterMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Today")
                        .description(null)
                        .filter(new Filter(Filter.Type.TODAY))
                        .build()
        );

        filterMenuItemList.add(
                new FilterMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Tomorrow")
                        .description(null)
                        .filter(new Filter(Filter.Type.TOMORROW))
                        .build()
        );

        filterMenuItemList.add(
                new FilterMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Week")
                        .description(null)
                        .filter(new Filter(Filter.Type.WEEK))
                        .build()
        );

        filterMenuItemList.add(
                new FilterMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Overdue")
                        .description(null)
                        .filter(new Filter(Filter.Type.OVERDUE))
                        .build()
        );

        filterMenuItemList.add(
                new FilterMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Done")
                        .description(null)
                        .filter(new Filter(Filter.Type.DONE))
                        .build()
        );
        return filterMenuItemList;
    }

}
