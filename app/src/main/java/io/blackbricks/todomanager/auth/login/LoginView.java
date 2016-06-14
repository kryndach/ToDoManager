package io.blackbricks.todomanager.auth.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public interface LoginView extends MvpView {
    void showLoginProgress();
    void hideLoginProgress();
    void openRegistration();
}
