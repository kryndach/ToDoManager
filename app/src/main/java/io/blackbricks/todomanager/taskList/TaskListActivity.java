package io.blackbricks.todomanager.taskList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.dagger.ToDoManagerModule;
import io.blackbricks.todomanager.database.DatabaseModule;
import io.blackbricks.todomanager.events.TaskListEnterEvent;
import io.blackbricks.todomanager.events.TaskListPushEvent;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.model.GroupProvider;
import io.blackbricks.todomanager.utils.BuildUtils;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class TaskListActivity extends BaseActivity {

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.contentView)
    ViewGroup contentView;

    ActionBarDrawerToggle drawerToggle;

    @Inject
    EventBus eventBus;

    @Inject
    GroupProvider groupProvider;

    private TaskListComponent taskListComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus.register(this);
        setContentView(R.layout.activity_task_list);

        if (savedInstanceState == null) {
            // Activity Transitions
            if (BuildUtils.isMinApi21()) {
                postponeEnterTransition();
            }

            Filter.Type type = Filter.Type.INBOX;
            String title = type.toString();

            final TaskListFragment fragment = new TaskListFragmentBuilder(title, type).build();

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                    R.string.drawer_close);
            drawerLayout.setDrawerListener(drawerToggle);


            toolbar.inflateMenu(R.menu.task_list_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.done) {
                        fragment.done();
                        return true;
                    }
                    return false;
                }
            });
            toolbarTitle.setText(title);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentView, fragment)
                    .commit();
        }
    }

    @Subscribe
    void onTaskListPushEvent(TaskListPushEvent event) {
        TaskListFragmentBuilder fragmentBuilder = new TaskListFragmentBuilder(event.title, event.type);
        if (event.groupId != null) {
            fragmentBuilder.groupId(event.groupId);
        }
        final TaskListFragment fragment = fragmentBuilder.build();

        drawerLayout.closeDrawers();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.done) {
                    fragment.done();
                    return true;
                }
                return false;
            }
        });
        toolbarTitle.setText(event.title);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .replace(R.id.contentView, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Subscribe
    void onTaskListEnterEvent(TaskListEnterEvent event) {
        final TaskListFragment fragment = (TaskListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentView);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.done) {
                    fragment.done();
                    return true;
                }
                return false;
            }
        });
        toolbarTitle.setText(event.title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void injectDependencies() {
        taskListComponent = DaggerTaskListComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .build();
        taskListComponent.inject(this);
    }
}
