package io.blackbricks.todomanager.menu.model;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.menu.model.items.FilterMenuItem;
import io.blackbricks.todomanager.menu.model.items.GroupMenuItem;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.GroupProvider;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.internal.operators.OperatorAny;
import rx.internal.operators.OperatorMap;

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

        ArrayList<FilterMenuItem> filterMenuItemList = getFilterMenuItems();

        ArrayList<OptionalMenuItem> optionalMenuItemList = getOptionalMenuItems();

        final ArrayList<GroupMenuItem> groupMenuItemList = new ArrayList<>();

        final Menu menu = new Menu.Builder()
                .filterMenuItemList(filterMenuItemList)
                .groupMenuItemList(groupMenuItemList)
                .optionalMenuItemList(optionalMenuItemList)
                .build();

        return groupProvider.getGroups()
                .flatMap(new Func1<List<Group>, Observable<Group>>() {
                    @Override
                    public Observable<Group> call(List<Group> groups) {
                        return Observable.from(groups);
                    }
                })
                .map(new Func1<Group, GroupMenuItem>() {
                    @Override
                    public GroupMenuItem call(Group group) {
                        return Menu.getGroupMenuItem(group);
                    }
                })
                .toList()
                .map(new Func1<List<GroupMenuItem>, Menu>() {
                    @Override
                    public Menu call(List<GroupMenuItem> groupMenuItems) {
                        menu.getGroupMenuItemList().clear();
                        menu.getGroupMenuItemList().addAll(groupMenuItems);
                        return menu;
                    }
                });
    }

    @NonNull
    private ArrayList<OptionalMenuItem> getOptionalMenuItems() {
        ArrayList<OptionalMenuItem> optionalMenuItemList = new ArrayList<>();

        optionalMenuItemList.add(
                new OptionalMenuItem.Builder()
                        .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                        .title("Support")
                        .description(null)
                        .type(OptionalMenuItem.Type.SUPPORT)
                        .build()
        );
        return optionalMenuItemList;
    }

    @NonNull
    private ArrayList<FilterMenuItem> getFilterMenuItems() {
        ArrayList<FilterMenuItem> filterMenuItemList = new ArrayList<>();

        Filter.Type[] filterList = {
                Filter.Type.INBOX,
                Filter.Type.TODAY,
                Filter.Type.TOMORROW,
                Filter.Type.WEEK,
                Filter.Type.HOT,
                Filter.Type.DONE,
                Filter.Type.OVERDUE,
        };
        for (Filter.Type type : filterList) {
            filterMenuItemList.add(
                    new FilterMenuItem.Builder()
                            .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                            .title(type.toString())
                            .description(null)
                            .filter(new Filter(type))
                            .build()
            );
        }

        return filterMenuItemList;
    }

}
