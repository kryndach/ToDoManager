package io.blackbricks.todomanager.auth.login;

import dagger.Component;
import io.blackbricks.todomanager.dagger.ActivityScope;
import io.blackbricks.todomanager.dagger.AppComponent;

/**
 * Created by yegorkryndach on 09/06/16.
 */

@ActivityScope
@Component(
        dependencies = AppComponent.class
)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}