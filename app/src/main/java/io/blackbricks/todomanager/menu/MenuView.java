package io.blackbricks.todomanager.menu;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public interface MenuView extends MvpLceView<Menu> {
    void selectFilterType(Filter.Type type);
    void selectGroup(int groupId);
}
