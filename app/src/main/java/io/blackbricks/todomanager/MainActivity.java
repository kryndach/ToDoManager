package io.blackbricks.todomanager;

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
import io.blackbricks.todomanager.menu.MenuFragment;
import io.blackbricks.todomanager.taskList.TaskListActivity;

public class MainActivity extends AppCompatActivity {

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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new Builderdddd, null)
                .commit();
    }
}
