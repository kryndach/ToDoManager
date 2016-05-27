package io.blackbricks.todomanager.taskList.model;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.Task;
import io.blackbricks.todomanager.model.TaskProvider;
import rx.Observable;
import rx.functions.Func1;

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
                        if(type == Filter.Type.WEEK) {
                            ArrayList<Pair<String, ArrayList<Task>>> sectionList =
                                    TaskListPresentationProvider.this.getSectionList(tasks);
                            return new TaskListPresentation.Builder()
                                    .sectionList(sectionList)
                                    .build();
                        } else {
                            ArrayList<Pair<String, ArrayList<Task>>> sectionList =
                                    TaskListPresentationProvider.this.getSimpleSectionList(tasks);
                            return new TaskListPresentation.Builder()
                                    .sectionList(sectionList)
                                    .build();
                        }
                    }
                });
    }

    private ArrayList<Pair<String, ArrayList<Task>>> getSectionList(List<Task> tasks) {
        ArrayList<Pair<String, ArrayList<Task>>> sectionList = new ArrayList<>();
        sectionList.add(new Pair<String, ArrayList<Task>>(null, new ArrayList<>(tasks)));
        return sectionList;
    }

    private ArrayList<Pair<String, ArrayList<Task>>> getSimpleSectionList(List<Task> tasks) {
        ArrayList<Pair<String, ArrayList<Task>>> sectionList = new ArrayList<>();
        sectionList.add(new Pair<String, ArrayList<Task>>(null, new ArrayList<>(tasks)));
        return sectionList;
    }

}
