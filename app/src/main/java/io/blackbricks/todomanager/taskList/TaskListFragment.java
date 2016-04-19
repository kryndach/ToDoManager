package io.blackbricks.todomanager.taskList;

import android.widget.LinearLayout;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;

import io.blackbricks.todomanager.base.view.BaseLceFragment;
import io.blackbricks.todomanager.taskList.model.TaskList;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListFragment extends BaseLceFragment<LinearLayout,TaskList,TaskListView,TaskListPresenter> {

    @Override
    protected int getLayoutRes() {
        return 0;
    }

    @Override
    public LceViewState<TaskList, TaskListView> createViewState() {
        return null;
    }

    @Override
    public TaskList getData() {
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
    public void setData(TaskList data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
