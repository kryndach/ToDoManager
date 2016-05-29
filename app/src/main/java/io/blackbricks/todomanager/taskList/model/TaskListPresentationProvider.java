package io.blackbricks.todomanager.taskList.model;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.blackbricks.todomanager.R;
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
    Context context;

    @Inject
    public TaskListPresentationProvider() {
    }

    public Observable<TaskListPresentation> getTaskListPresentation(final Filter.Type type, @Nullable Integer groupId) {
        return taskProvider.getTasks(type, groupId)
                .map(new Func1<List<Task>, TaskListPresentation>() {
                    @Override
                    public TaskListPresentation call(List<Task> tasks) {
                        if (type == Filter.Type.WEEK) {
                            ArrayList<TaskListSection> sectionList =
                                    TaskListPresentationProvider.this.getSectionList(tasks);
                            return new TaskListPresentation.Builder()
                                    .sectionList(sectionList)
                                    .build();
                        } else {
                            ArrayList<TaskListSection> sectionList =
                                    TaskListPresentationProvider.this.getSimpleSectionList(tasks);
                            return new TaskListPresentation.Builder()
                                    .sectionList(sectionList)
                                    .build();
                        }
                    }
                });
    }

    private ArrayList<TaskListSection> getSectionList(List<Task> tasks) {
        return getTaskListSectionArrayList(tasks);
    }

    private ArrayList<TaskListSection> getSimpleSectionList(List<Task> tasks) {
        ArrayList<TaskListSection> sectionList = new ArrayList<>();
        sectionList.add(new TaskListSection.Builder()
                .taskList(new ArrayList<>(tasks))
                .build());
        return sectionList;
    }

    private String getSectionName(Date date){
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);

        Calendar currentCalendar = Calendar.getInstance();

        if(currentCalendar.get(Calendar.DAY_OF_YEAR) == targetCalendar.get(Calendar.DAY_OF_YEAR) ){
            return context.getString(R.string.today);
        }else if(currentCalendar.get(Calendar.DAY_OF_YEAR) + 1 == targetCalendar.get(Calendar.DAY_OF_YEAR) ){
            return context.getString(R.string.tomorrow);
        }else
            return DateFormat.format(context.getString(R.string.SectionDateFormat), date).toString();
    }

    private Date getSectionDay(Date date){
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);

        targetCalendar.set(Calendar.HOUR, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        targetCalendar.set(Calendar.SECOND, 0);
        targetCalendar.set(Calendar.MILLISECOND, 0);

        return targetCalendar.getTime();
    }

    private ArrayList<TaskListSection> getTaskListSectionArrayList(List<Task> tasks) {
        HashMap<Date, ArrayList<Task>> hashMap = new HashMap<>();

        for(Task task : tasks) {
            Date dayOfTask = getSectionDay(task.getDateDeadline());

            ArrayList<Task> taskArrayList;
            if(hashMap.containsKey(dayOfTask)) {
                taskArrayList = hashMap.get(dayOfTask);
            } else {
                taskArrayList = new ArrayList<>();
                hashMap.put(dayOfTask, taskArrayList);
            }

            taskArrayList.add(task);
        }

        ArrayList<TaskListSection> taskListSectionArrayList = new ArrayList<>();

        ArrayList<Date> days = new ArrayList<>(hashMap.keySet());
        Collections.sort(days);

        for(Date day : days) {
            TaskListSection taskListSection = new TaskListSection.Builder()
                    .title(getSectionName(day))
                    .taskList(hashMap.get(day))
                    .build();
            taskListSectionArrayList.add(taskListSection);
        }

        return taskListSectionArrayList;
    }
}
