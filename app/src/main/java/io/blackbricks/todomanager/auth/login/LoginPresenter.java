package io.blackbricks.todomanager.auth.login;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import io.blackbricks.todomanager.api.TokenService;
import io.blackbricks.todomanager.api.response.LoginResponse;
import io.blackbricks.todomanager.api.service.AuthService;
import io.blackbricks.todomanager.auth.login.model.LoginPresentation;
import rx.Observable;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {

    @Inject
    TokenService mTokenService;
    @Inject
    AuthService mAuthService;

    private LoginPresentation mLoginPresentation;

    @Inject
    public LoginPresenter() {
    }

    public void login(){
        String userName = mLoginPresentation.getUsername();
        String password = mLoginPresentation.getPassword();
        Observable<LoginResponse> loginObservable = mAuthService.login(userName, password);
        mTokenService.autoToken(loginObservable);
    }
}
