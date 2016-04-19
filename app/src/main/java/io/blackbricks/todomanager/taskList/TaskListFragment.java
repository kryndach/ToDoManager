package io.blackbricks.todomanager.taskList;

import android.widget.LinearLayout;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;

import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.dagger.NavigationModule;
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
    Group group;

    private TaskListComponent taskListComponent;

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<TaskListPresentation, TaskListView> createViewState() {
        return null;
    }

    @Override
    public TaskListPresentation getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public TaskListPresenter createPresenter() {
        return null;
    }

    @Override
    protected void injectDependencies() {
        taskListComponent = DaggerTaskListComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .navigationModule(new NavigationModule())
                .build();
        taskListComponent.inject(this);
    }

    @Override
    public void setData(TaskListPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
