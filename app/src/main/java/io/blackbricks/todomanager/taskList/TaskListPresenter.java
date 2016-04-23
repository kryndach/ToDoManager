package io.blackbricks.todomanager.taskList;

import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.TaskProvider;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;
import io.blackbricks.todomanager.taskList.model.TaskListPresentationProvider;
import rx.functions.Action1;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListPresenter extends BaseRxLcePresenter<TaskListView, TaskListPresentation> {

    private TaskListPresentationProvider taskListPresentationProvider;

    @Inject
    public TaskListPresenter(TaskListPresentationProvider taskListPresentationProvider) {
        this.taskListPresentationProvider = taskListPresentationProvider;
    }

    public void loadTaskList(Filter.Type type, @Nullable Integer groupId) {
        subscribe(taskListPresentationProvider.getTaskListPresentation(type, groupId), false);
    }

    @Override
    protected void onNext(TaskListPresentation data) {
        if (isViewAttached()) {
            getView().setData(data);
            getView().showContent();
        }
    }
}
