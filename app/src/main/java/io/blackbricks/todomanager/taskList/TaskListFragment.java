package io.blackbricks.todomanager.taskList;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import io.blackbricks.todomanager.IntentStarter;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.background.Alarm;
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

    @Arg
    String title;

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

    @Inject
    Alarm alarm;

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
        eventBus.post(new TaskListEnterEvent(type, groupId, title));
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
                .build();
        taskListComponent.inject(this);
    }

    @Override
    public void setData(TaskListPresentation data) {
        this.taskListPresentation = data;
        taskListAdapter.setSectionList(data.getSectionList());
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
        intentStarter.createTask(getActivity(), groupId);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void onTaskClicked(Task task, int position) {
        intentStarter.editTask(getActivity(), task.getId(), task.getGroupId());
    }

    @Override
    public void onTaskDelete(final Task task, final int section, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(String.format("Are you sure to remove %s task?", task.getTitle()));

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbOperationHelper.deleteTask(task.getId());
                taskListAdapter.notifyItemRemoved(position);
                ArrayList taskList = taskListPresentation.getSectionList().get(section).getTaskList();
                taskList.remove(task);
                if(taskList.size() == 0) {
                    taskListPresentation.getSectionList().remove(section);
                }
                taskListAdapter.closeAllItems();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
