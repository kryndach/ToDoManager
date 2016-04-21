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
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.GroupProvider;
import rx.Observable;
import rx.Subscriber;
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

        List<FilterMenuItem> filterMenuItemList = getFilterMenuItems();

        List<OptionalMenuItem> optionalMenuItemList = getOptionalMenuItems();

        final List<GroupMenuItem> groupMenuItemList = new ArrayList<>();

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

                        String description = "Tasks " + group.getTaskCount();
                        if(group.getHotTaskCount() > 0) {
                            description = "Hot " + group.getHotTaskCount() + ", " + description;
                        }

                        return new GroupMenuItem.Builder()
                                .iconRes(R.drawable.ic_assignment_turned_in_black_24dp)
                                .title(group.getName())
                                .description(description)
                                .group(group)
                                .build();
                    }
                })
                .toList().concatMap(new Func1<List<GroupMenuItem>, Observable<Menu>>() {
                    @Override
                    public Observable<Menu> call(List<GroupMenuItem> groupMenuItems) {
                        menu.getGroupMenuItemList().addAll(groupMenuItemList);
                        return Observable.just(menu);
                    }
                });
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
