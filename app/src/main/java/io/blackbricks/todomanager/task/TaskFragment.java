package io.blackbricks.todomanager.task;

import android.widget.LinearLayout;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.ParcelableDataLceViewState;

import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.task.model.TaskPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskFragment extends BaseLceFragment<LinearLayout, TaskPresentation, TaskView, TaskPresenter>
        implements TaskView {

    @Arg(required = false)
    Integer taskId;

    private TaskComponent taskComponent;
    private TaskPresentation taskPresentation;

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<TaskPresentation, TaskView> createViewState() {
        return new ParcelableDataLceViewState<>();
    }

    @Override
    public TaskPresentation getData() {
        return taskPresentation;
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
        this.taskPresentation = data;
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadTask(taskId);
    }

    @Override
    public void done() {

    }
}
