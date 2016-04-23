package io.blackbricks.todomanager.taskList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.utils.BuildUtils;

/**
 * Created by yegorkryndach on 14/04/16.
 */
public class TaskListActivity extends BaseActivity {

    public static final String KEY_FILTER =
            "io.blackbricks.todomanager.taskList.TaskListActivity.FILTER";

    public static final String KEY_GROUP_ID =
            "io.blackbricks.todomanager.taskList.TaskListActivity.GROUP_ID";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

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

//        if (savedInstanceState == null) {
//            Mail mail = getIntent().getParcelableExtra(KEY_MAIL);
//            Person sender = mail.getSender();
//
//            DetailsFragment fragment =
//                    new DetailsFragmentBuilder(mail.getDate().getTime(), mail.getId(), sender.getEmail(),
//                            sender.getName(), sender.getImageRes(), mail.isStarred(), mail.getSubject()).build();
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.contentView, fragment)
//                    .commit();
//        }
    }
}
