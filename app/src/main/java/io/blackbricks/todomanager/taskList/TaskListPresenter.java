package io.blackbricks.todomanager.taskList;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListPresenter extends BaseRxLcePresenter<TaskListView, TaskListPresentation> {
    @Inject
    public TaskListPresenter() {
    }
}
