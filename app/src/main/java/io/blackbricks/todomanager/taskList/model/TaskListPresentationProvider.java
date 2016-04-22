package io.blackbricks.todomanager.taskList.model;

import javax.inject.Inject;

import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.Observable;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListPresentationProvider {

    @Inject
    TaskProvider taskProvider;

    @Inject
    public TaskListPresentationProvider() {
    }

    public Observable<TaskListPresentation> getTaskListPresentation(Filter.Type type, String groupId) {
        return null;
    }
}
