package io.blackbricks.todomanager.task.model;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 25/04/16.
 */
public class TaskPresentationProvider {

    @Inject
    TaskProvider taskProvider;

    @Inject
    public TaskPresentationProvider() {
    }

    public Observable<TaskPresentation> getTaskPresentation(@Nullable Integer taskId, @Nullable Integer groupId) {
        return taskProvider.getTask(taskId)
                .map(new Func1<Task, TaskPresentation>() {
                    @Override
                    public TaskPresentation call(Task task) {
                        return new TaskPresentation.Builder().task(task).build();
                    }
                });
    }
}
