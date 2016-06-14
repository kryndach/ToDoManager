package io.blackbricks.todomanager.auth.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseActivity;
import io.blackbricks.todomanager.databinding.ActivityLoginBinding;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class LoginActivity extends BaseActivity {

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
        LoginComponent loginComponent = DaggerLoginComponent.builder()
                .appComponent(ToDoManagerApp.getAppComponent())
                .build();
        loginComponent.inject(this);
    }

    public void onClickLogin(View view) {
        mLoginPresenter.login();
    }
}
