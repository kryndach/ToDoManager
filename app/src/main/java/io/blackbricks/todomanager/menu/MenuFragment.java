package io.blackbricks.todomanager.menu;

import android.app.AlertDialog;
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

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;
import io.blackbricks.todomanager.events.TaskListPushEvent;
import io.blackbricks.todomanager.menu.model.Menu;
import io.blackbricks.todomanager.menu.model.items.OptionalMenuItem;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;

/**
 * Created by yegorkryndach on 16/04/16.
 */
public class MenuFragment extends BaseLceFragment<RecyclerView, Menu, MenuView, MenuPresenter>
        implements MenuView, MenuAdapter.FilterClickListener, MenuAdapter.GroupClickListener,
        MenuAdapter.OptionalClickListener, MenuAdapter.GroupLongClickListener {

    private MenuComponent menuComponent;
    private Menu menu;
    private MenuAdapter menuAdapter;

    @Bind(R.id.contentView)
    ViewGroup contentView;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    IntentStarter intentStarter;

    @Inject
    DatabaseOperationHelper dbOperationHelper;

    @Inject
    EventBus eventBus;

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

        menuAdapter = new MenuAdapter(getActivity(), menu, this, this, this, this);
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
                .appComponent(ToDoManagerApp.getAppComponent())
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
        presenter.loadMenu(pullToRefresh);
    }

    @Override
    public void onFilterClicked(Filter filter) {
        eventBus.post(new TaskListPushEvent(filter.getType(), null, filter.getType().toString()));
    }

    @Override
    public void onGroupClicked(Group group) {
        eventBus.post(new TaskListPushEvent(Filter.Type.GROUP, group.getGroupId(), group.getName()));
    }

    @Override
    public void onOptionalClicked(OptionalMenuItem.Type type) {
        menuAdapter.selectOptional(type);
    }

    @OnClick(R.id.addButton)
    void onAddClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuFragment.this.getContext());
        builder.setTitle(R.string.name);

        final int groupCount = menu.getGroupMenuItemList().size();

        final EditText input = new EditText(MenuFragment.this.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String resultText = input.getText().toString();
                if (resultText.length() > 0) {
                    dbOperationHelper.putGroup(new Group.Builder()
                            .name(resultText)
                            .taskCount(0)
                            .hotTaskCount(0)
                            .order(groupCount)
                            .build());
                } else {
                    new AlertDialog.Builder(MenuFragment.this.getContext())
                            .setTitle(R.string.error)
                            .setMessage(R.string.need_some_symbols)
                            .show();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show();
    }

    @Override
    public void selectFilterType(Filter.Type type) {
        menuAdapter.selectFilterType(type);
    }

    @Override
    public void selectGroup(int groupId) {
        menuAdapter.selectGroup(groupId);
    }

    @Override
    public void onGroupLongClicked(final Group group) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuFragment.this.getContext());
        builder.setTitle(String.format(getContext().getString(R.string.remove_group_question), group.getName()));

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbOperationHelper.deleteGroup(group.getGroupId(), true);
            }
        });

        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbOperationHelper.deleteGroup(group.getGroupId(), false);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
