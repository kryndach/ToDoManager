package io.blackbricks.todomanager.auth.login;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.ToDoManagerAppComponent;

/**
 * Created by yegorkryndach on 09/06/16.
 */

@ActivityScope
@Component(
        dependencies = ToDoManagerAppComponent.class
)
public interface LoginComponent {
    void inject(LoginFragment loginFragment);
}