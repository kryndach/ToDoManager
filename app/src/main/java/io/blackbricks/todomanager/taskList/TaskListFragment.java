package io.blackbricks.todomanager.taskList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.database.DatabaseOperationHelper;
import io.blackbricks.todomanager.events.TaskListEnterEvent;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListFragment extends BaseLceFragment<LinearLayout, TaskListPresentation, TaskListView, TaskListPresenter>
        implements TaskListView, TaskListAdapter.TaskClickListener,
        SwipeRefreshLayout.OnRefreshListener, TaskListAdapter.TaskDeleteListener,
        TaskListAdapter.TaskDoneListener, TaskListAdapter.TaskHotListener {

    @Arg
    Filter.Type type;

    @Arg(required = false)
    Integer groupId;

    @Bind(R.id.contentView)
    ViewGroup contentView;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    IntentStarter intentStarter;

    @Inject
    DatabaseOperationHelper dbOperationHelper;

    @Inject
    EventBus eventBus;

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

        swipeRefreshLayout.setOnRefreshListener(this);

        taskListAdapter = new TaskListAdapter(getActivity(), null, this, this, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(taskListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (taskListAdapter.getOpenItems().size() > 0) {
                    taskListAdapter.closeAllExcept(null);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        eventBus.post(new TaskListEnterEvent(type, groupId));
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
        taskListAdapter.closeAllExcept(null);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadTaskList(type, groupId, pullToRefresh);
    }

    @Override
    public void showContent() {
        super.showContent();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void done() {

    }

    @OnClick(R.id.addButton)
    void onAddClick() {
        intentStarter.createTask(getActivity());
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onTaskClicked(Task task, int position) {
        intentStarter.editTask(getActivity(), task.getId());
    }

    @Override
    public void onTaskDelete(Task task, int position) {
        dbOperationHelper.deleteTask(task.getId());
        taskListAdapter.notifyItemRemoved(position);
        taskListPresentation.getTaskList().remove(task);
        taskListAdapter.closeAllItems();
    }

    @Override
    public void onTaskDone(Task task, int position) {
        if (task.getStatus() != Task.Status.DONE) {
            task.setStatus(Task.Status.DONE);
        } else {
            task.setStatus(Task.Status.UNDONE);
        }
        dbOperationHelper.putTask(task);
        taskListAdapter.closeAllItems();
        taskListAdapter.notifyItemChanged(position);
    }

    @Override
    public void onTaskHot(Task task, int position) {
        if (task.getStatus() != Task.Status.HOT) {
            task.setStatus(Task.Status.HOT);
        } else {
            task.setStatus(Task.Status.UNDONE);
        }
        dbOperationHelper.putTask(task);
        taskListAdapter.closeAllItems();
        taskListAdapter.notifyItemChanged(position);
    }
}
