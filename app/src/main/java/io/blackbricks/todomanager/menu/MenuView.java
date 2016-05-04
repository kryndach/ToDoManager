package io.blackbricks.todomanager.menu;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public interface MenuView extends MvpLceView<Menu> {
    void insertGroup(Group group);
    void updateGroup(Group group);
    void removeGroup(Integer groupId);
}
