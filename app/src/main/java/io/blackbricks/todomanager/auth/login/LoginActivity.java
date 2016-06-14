package io.blackbricks.todomanager.auth.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import javax.inject.Inject;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.api.response.LoginResponse;
import io.blackbricks.todomanager.api.service.AuthService;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.databinding.ActivityLoginBinding;
import rx.functions.Action1;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class LoginActivity extends BaseActivity {

    private LoginComponent mLoginComponent;
    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setHandler(this);
        binding.setPresenter(mLoginPresenter);
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
