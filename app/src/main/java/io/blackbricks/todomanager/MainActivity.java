package io.blackbricks.todomanager;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import icepick.Icicle;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.menu.MenuFragment;
import io.blackbricks.todomanager.model.Filter;
import io.blackbricks.todomanager.taskList.TaskListActivity;
import io.blackbricks.todomanager.taskList.TaskListFragment;
import io.blackbricks.todomanager.taskList.TaskListFragmentBuilder;
import io.blackbricks.todomanager.taskList.model.TaskListPresentation;

public class MainActivity extends BaseActivity {

    @Icicle
    String toolbarTitle;

    @Inject
    IntentStarter intentStarter;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.contentView)
    ViewGroup contentView;

    ActionBarDrawerToggle drawerToggle;
    private MainActivityComponent mainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView,
                        new TaskListFragmentBuilder(Filter.Type.INBOX).build(),
                        null)
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
