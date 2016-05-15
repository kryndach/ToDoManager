package io.blackbricks.todomanager.task;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.blackbricks.todomanager.base.presenter.BaseRxLcePresenter;
import io.blackbricks.todomanager.model.TaskProvider;
import io.blackbricks.todomanager.task.model.TaskPresentation;
import io.blackbricks.todomanager.task.model.TaskPresentationProvider;
import rx.Observable;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskPresenter extends BaseRxLcePresenter<TaskView, TaskPresentation> {

    @Inject
    TaskPresentationProvider taskPresentationProvider;

    @Inject
    public TaskPresenter() {
    }

    public void loadTask(@Nullable Integer taskId, @Nullable Integer groupId) {
        subscribe(taskPresentationProvider.getTaskPresentation(taskId, groupId), false);
    }
}
