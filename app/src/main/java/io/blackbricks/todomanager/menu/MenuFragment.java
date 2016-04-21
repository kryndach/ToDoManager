package io.blackbricks.todomanager.menu;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.AbsLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;

import butterknife.Bind;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.DaggerToDoManagerAppComponent;
import io.blackbricks.todomanager.database.DatabaseHelper;
import io.blackbricks.todomanager.database.DatabaseModule;
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

    @Inject
    BriteDatabase database;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuFragment.this.getContext());
                builder.setTitle("Name");

                final EditText input = new EditText(MenuFragment.this.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String resultText = input.getText().toString();
                        if (resultText.length() > 0) {
                            ContentValues values = new ContentValues();
                            values.put(DatabaseHelper.GROUP_NAME_COLUMN, resultText);
                            values.put(DatabaseHelper.GROUP_TASK_COUNT_COLUMN, 0);
                            values.put(DatabaseHelper.GROUP_HOT_TASK_COUNT_COLUMN, 0);
                            database.insert("groups", values);
                        } else {
                            new AlertDialog.Builder(MenuFragment.this.getContext())
                                    .setTitle("Error")
                                    .setMessage("Need some symbols!")
                                    .show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
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
