package io.blackbricks.todomanager.menu;

import android.database.Observable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.events.GroupPuttedEvent;
import io.blackbricks.todomanager.events.GroupRemovedEvent;
import io.blackbricks.todomanager.events.GroupsUpdatedEvent;
import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.menu.model.MenuProvider;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuPresenter extends BaseRxLcePresenter<MenuView, Menu> {

    @Inject
    EventBus eventBus;

    private MenuProvider menuProvider;

    @Inject
    public MenuPresenter(MenuProvider menuProvider) {
        this.menuProvider = menuProvider;
    }

    public void loadMenu() {
        subscribe(menuProvider.getMenu(), false);
    }

    @Override
    public void attachView(MenuView view) {
        super.attachView(view);
        eventBus.register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        eventBus.unregister(this);
    }

    @Subscribe
    void onGroupPuttedEvent(GroupPuttedEvent event) {
        if (isViewAttached()) {
            getView().loadData(false);
        }
    }

    @Subscribe
    void onGroupRemovedEvent(GroupRemovedEvent event) {
        if (isViewAttached()) {
            getView().loadData(false);
        }
    }

    @Subscribe
    void onGroupsUpdatedEvent(GroupsUpdatedEvent event) {
        if (isViewAttached()) {
            getView().loadData(false);
        }
    }
}
