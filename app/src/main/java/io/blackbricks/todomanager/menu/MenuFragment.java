package io.blackbricks.todomanager.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.AbsLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import javax.inject.Inject;

import butterknife.Bind;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.DaggerToDoManagerAppComponent;
import io.blackbricks.todomanager.dagger.NavigationModule;
import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuFragment extends BaseLceFragment<RecyclerView, Menu, MenuView, MenuPresenter>
        implements MenuView, MenuAdapter.FilterClickListener, MenuAdapter.GroupClickListener,
        MenuAdapter.OptionalClickListener {

    private MenuComponent menuComponent;
    private Menu menu;
    private MenuAdapter menuAdapter;

    @Bind(R.id.contentView)
    ViewGroup contentView;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.addButton)
    FloatingActionButton addButton;

    @Inject
    IntentStarter intentStarter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_menu;
    }

    @Override
    public LceViewState<Menu, MenuView> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        menuAdapter = new MenuAdapter(getActivity(), menu, this, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);
    }

    @Override
    public Menu getData() {
        return menu;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public MenuPresenter createPresenter() {
        return menuComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        menuComponent = DaggerMenuComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .navigationModule(new NavigationModule())
                .build();
        menuComponent.inject(this);
    }

    @Override
    public void setData(Menu data) {
        this.menu = data;
        menuAdapter.setMenu(data);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadMenu();
    }

    @Override
    public void onFilterClicked(Filter filter) {
        intentStarter.showTaskList(getActivity(), filter);
    }

    @Override
    public void onGroupClicked(Group group) {
        intentStarter.showTaskList(getActivity(), group);
    }

    @Override
    public void onOptionalClicked(OptionalMenuItem.Type type) {

    }
}
