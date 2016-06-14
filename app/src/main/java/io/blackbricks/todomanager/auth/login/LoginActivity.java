package io.blackbricks.todomanager.auth.login;

import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.api.response.LoginResponse;
import io.blackbricks.todomanager.api.service.AuthService;
import io.blackbricks.todomanager.base.view.BaseActivity;
import rx.functions.Action1;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class LoginActivity extends BaseActivity {

    private LoginComponent mLoginComponent;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void injectDependencies() {
        mLoginComponent = DaggerLoginComponent.builder()
                .appComponent(ToDoManagerApp.getAppComponent())
                .build();
        mLoginComponent.inject(this);
    }

    public void onClickLogin(View view) {
        mLoginPresenter.login();
    }
}
