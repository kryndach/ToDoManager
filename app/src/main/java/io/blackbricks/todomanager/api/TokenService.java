package io.blackbricks.todomanager.api;

import java.net.HttpURLConnection;

import io.blackbricks.todomanager.api.response.LoginResponse;
import io.blackbricks.todomanager.api.service.AuthService;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 13/06/16.
 */
public class TokenService {

    AuthService mAuthService;
    UserSessionManager mSessionManager;

    public TokenService(AuthService mAuthService, UserSessionManager mSessionManager) {
        this.mAuthService = mAuthService;
        this.mSessionManager = mSessionManager;
    }

    public <T> Observable<T> autoToken(Observable<T> request){
        return request.onErrorResumeNext(refreshTokenAndRetry(request));
    }

    public Observable<LoginResponse> refreshToken() {
        return mAuthService
                .login(mSessionManager.getUserName(), mSessionManager.getPassword())
                .doOnNext(new Action1<LoginResponse>() {
                    @Override
                    public void call(LoginResponse loginResponse) {
                        mSessionManager.setToken(loginResponse.getToken());
                    }
                });
    }

    private  <T> Func1<Throwable,? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> toBeResumed) {
        return new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                // Here check if the error thrown is a HTTP_UNAUTHORIZED
                if (throwable instanceof RetrofitError) {
                    RetrofitError retrofitError = (RetrofitError) throwable;
                    int statusCode = retrofitError.getResponse().getStatus();
                    if(statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        return refreshToken().flatMap(new Func1<LoginResponse, Observable<? extends T>>() {
                            @Override
                            public Observable<? extends T> call(LoginResponse token) {
                                return toBeResumed;
                            }
                        });
                    }
                }
                // re-throw this error because it's not recoverable from here
                return Observable.error(throwable);
            }
        };
    }
}
