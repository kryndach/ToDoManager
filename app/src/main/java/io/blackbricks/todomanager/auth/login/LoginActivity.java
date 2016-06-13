package io.blackbricks.todomanager.auth.login;

import android.os.Bundle;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseActivity;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class LoginActivity extends BaseActivity {

    private LoginComponent loginComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void injectDependencies() {
        loginComponent = DaggerLoginComponent.builder()
                .appComponent(ToDoManagerApp.getAppComponent())
                .build();
        loginComponent.inject(this);
    }
}
