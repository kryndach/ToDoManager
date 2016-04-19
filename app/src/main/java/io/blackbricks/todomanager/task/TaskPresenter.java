package io.blackbricks.todomanager.task;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.task.model.TaskPresentation;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskPresenter extends BaseRxLcePresenter<TaskView, TaskPresentation> {
    @Inject
    public TaskPresenter() {
    }
}
