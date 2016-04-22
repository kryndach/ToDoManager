package io.blackbricks.todomanager.taskList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.melnykov.fab.FloatingActionButton;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListFragment extends BaseLceFragment<LinearLayout, TaskListPresentation, TaskListView, TaskListPresenter> {

    @Arg
    Filter.Type type;

    @Arg
    String groupId;

    @Bind(R.id.contentView)
    ViewGroup contentView;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.addButton)
    FloatingActionButton addButton;

    private TaskListPresentation taskListPresentation;
    private TaskListAdapter taskListAdapter;
    private TaskListComponent taskListComponent;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_task_list;
    }

    @Override
    public LceViewState<TaskListPresentation, TaskListView> createViewState() {
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

        taskListAdapter = new TaskListAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListAdapter);
    }

    @Override
    public TaskListPresentation getData() {
        return taskListPresentation;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public TaskListPresenter createPresenter() {
        return taskListComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        taskListComponent = DaggerTaskListComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .databaseModule(new DatabaseModule())
                .toDoManagerModule(new ToDoManagerModule(ToDoManagerApp.get(this.getActivity())))
                .build();
        taskListComponent.inject(this);
    }

    @Override
    public void setData(TaskListPresentation data) {
        this.taskListPresentation = data;
        taskListAdapter.setTaskList(data.getTaskList());
        taskListAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        taskListComponent.presenter().loadTaskList(type, groupId);
    }
}
