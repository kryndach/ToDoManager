package io.blackbricks.todomanager.taskList.model;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Group;
import io.blackbricks.todomanager.model.GroupProvider;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskListPresentationProvider {

    @Inject
    TaskProvider taskProvider;

    @Inject
    public TaskListPresentationProvider() {
    }

    public Observable<TaskListPresentation> getTaskListPresentation(final Filter.Type type, @Nullable Integer groupId) {
        return taskProvider.getTasks(type, groupId)
                .map(new Func1<List<Task>, TaskListPresentation>() {
                    @Override
                    public TaskListPresentation call(List<Task> tasks) {
                        return new TaskListPresentation.Builder()
                                .taskList(new ArrayList<>(tasks))
                                .build();
                    }
                });
    }
}
