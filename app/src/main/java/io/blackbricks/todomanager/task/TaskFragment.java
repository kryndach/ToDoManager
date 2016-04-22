package io.blackbricks.todomanager.task;

import android.widget.LinearLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;

import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.task.model.TaskPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskFragment extends BaseLceFragment<LinearLayout, TaskPresentation, TaskView, TaskPresenter> {

    private TaskComponent taskComponent;

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<TaskPresentation, TaskView> createViewState() {
        return null;
    }

    @Override
    public TaskPresentation getData() {
        return null;
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public TaskPresenter createPresenter() {
        return taskComponent.presenter();
    }

    @Override
    protected void injectDependencies() {
        taskComponent = DaggerTaskComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .build();
        taskComponent.inject(this);
    }

    @Override
    public void setData(TaskPresentation data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
