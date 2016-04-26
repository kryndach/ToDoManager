package io.blackbricks.todomanager.task;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.utils.BuildUtils;

/**
 * Created by yegorkryndach on 19/04/16.
 */
public class TaskActivity extends BaseActivity {

    public static final String KEY_TASK_ID =
            "io.blackbricks.todomanager.taskList.TaskActivity.TASK_ID";

    public static final String KEY_GROUP_ID =
            "io.blackbricks.todomanager.taskList.TaskActivity.GROUP_ID";

    public static final String KEY_TITLE =
            "io.blackbricks.todomanager.taskList.TaskActivity.TITLE";

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.contentView)
    ViewGroup contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Integer taskId = null;
            if (intent.hasExtra(KEY_TASK_ID)) {
                taskId = intent.getIntExtra(KEY_TASK_ID, 0);
            }
            Integer groupId = null;
            if (intent.hasExtra(KEY_TASK_ID)) {
                groupId = intent.getIntExtra(KEY_GROUP_ID, 0);
            }
            String title = intent.getStringExtra(KEY_TITLE);

            // Activity Transitions
            if (BuildUtils.isMinApi21()) {
                postponeEnterTransition();
            }

            TaskFragmentBuilder fragmentBuilder = new TaskFragmentBuilder();
            if (taskId != null) {
                fragmentBuilder.taskId(taskId);
            }
            if (groupId != null) {
                fragmentBuilder.groupId(groupId);
            }
            final TaskFragment fragment = fragmentBuilder.build();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentView, fragment)
                    .commit();

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

            toolbar.inflateMenu(R.menu.task_menu);
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
        }
    }

}
