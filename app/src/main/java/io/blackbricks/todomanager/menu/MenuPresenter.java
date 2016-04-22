package io.blackbricks.todomanager.menu;

import android.database.Observable;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.menu.model.MenuProvider;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuPresenter extends BaseRxLcePresenter<MenuView, Menu> {

    private MenuProvider menuProvider;

    @Inject
    public MenuPresenter(MenuProvider menuProvider) {
        this.menuProvider = menuProvider;
    }

    public void loadMenu() {
        subscribe(menuProvider.getMenu(), false);
    }

    @Override
    protected void onNext(Menu data) {
        if (isViewAttached()) {
            getView().setData(data);
            getView().showContent();
        }
    }
}
