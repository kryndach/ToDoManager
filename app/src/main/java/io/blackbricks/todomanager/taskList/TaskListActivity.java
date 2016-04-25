package io.blackbricks.todomanager.taskList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.utils.BuildUtils;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class TaskListActivity extends BaseActivity {

    public static final String KEY_FILTER =
            "io.blackbricks.todomanager.taskList.TaskListActivity.FILTER";

    public static final String KEY_GROUP_ID =
            "io.blackbricks.todomanager.taskList.TaskListActivity.GROUP_ID";

    public static final String KEY_TITLE =
            "io.blackbricks.todomanager.taskList.TaskListActivity.TITLE";

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.contentView)
    ViewGroup contentView;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Filter filter = intent.getParcelableExtra(KEY_FILTER);
            Integer groupId = null;
            if (intent.hasExtra(KEY_GROUP_ID)) {
                groupId = intent.getIntExtra(KEY_GROUP_ID, 0);
            }
            String title = intent.getStringExtra(KEY_TITLE);

            // Activity Transitions
            if (BuildUtils.isMinApi21()) {
                postponeEnterTransition();
            }

            boolean needBackNavigation = true;
            if(filter == null) {
                needBackNavigation = false;
                Filter.Type type = Filter.Type.INBOX;
                filter = new Filter(type);
                title = type.toString();
            }

            if(needBackNavigation) {
                toolbar.setNavigationIcon(BuildUtils.getBackArrowDrawable(this));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= 21) {
                            finishAfterTransition();
                        } else {
                            finish();
                        }
                    }
                });
            } else {
                drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                        R.string.drawer_close);
                drawerLayout.setDrawerListener(drawerToggle);
            }

            toolbar.inflateMenu(R.menu.task_list_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.done) {
                        // do stuff on done
                        return true;
                    }
                    return false;
                }
            });
            toolbarTitle.setText(title);

            TaskListFragmentBuilder fragmentBuilder = new TaskListFragmentBuilder(filter.getType());
            if (groupId != null) {
                fragmentBuilder.groupId(groupId);
            }
            TaskListFragment fragment = fragmentBuilder.build();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentView, fragment)
                    .commit();
        }
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }
}
