package io.blackbricks.todomanager.task;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

        // Activity Transitions
        if (BuildUtils.isMinApi21()) {
            postponeEnterTransition();
        }

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

        Intent intent = getIntent();
        Integer taskId = null;
        if (intent.hasExtra(KEY_TASK_ID)) {
            taskId = intent.getIntExtra(KEY_TASK_ID, 0);
        }
        Integer groupId = null;
        if (intent.hasExtra(KEY_GROUP_ID)) {
            groupId = intent.getIntExtra(KEY_GROUP_ID, 0);
        }
        String title = intent.getStringExtra(KEY_TITLE);

        if (savedInstanceState == null) {

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

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.done) {
                        fragment.done();
                        return true;
                    }
                    if (item.getItemId() == R.id.save) {
                        fragment.save();
                        return true;
                    }
                    return false;
                }
            });
        }

        if (taskId != null) {
            toolbar.inflateMenu(R.menu.edit_task_menu);
        } else {
            toolbar.inflateMenu(R.menu.create_task_menu);
        }

        toolbarTitle.setText(title);
    }

}
