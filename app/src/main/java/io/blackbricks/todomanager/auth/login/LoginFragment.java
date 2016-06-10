package io.blackbricks.todomanager.auth.login;

import io.blackbricks.todomanager.R;
import io.blackbricks.todomanager.ToDoManagerApp;
import io.blackbricks.todomanager.base.view.BaseFragment;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class LoginFragment extends BaseFragment implements LoginView {

    private LoginComponent loginComponent;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected void injectDependencies() {
        loginComponent = DaggerLoginComponent.builder()
                .toDoManagerAppComponent(ToDoManagerApp.getAppComponent())
                .build();
        loginComponent.inject(this);
    }
}
