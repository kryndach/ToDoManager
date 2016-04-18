package io.blackbricks.todomanager.menu;

import android.database.Observable;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.menu.model.Menu;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuPresenter extends BaseRxLcePresenter<MenuView, Menu> {
    @Inject
    public MenuPresenter() {
    }

    public void loadMenu() {
        Observable<Menu> fff;

        subscribe(fff, false);
    }
}
