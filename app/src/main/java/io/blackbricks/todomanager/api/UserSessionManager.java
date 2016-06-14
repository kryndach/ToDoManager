package io.blackbricks.todomanager.api;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.blackbricks.todomanager.api.service.AuthService;
import okhttp3.ResponseBody;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yegorkryndach on 13/06/16.
 */
public class UserSessionManager {
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "token";

    private SharedPreferences mSharedPreferences;

    public UserSessionManager(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public String getUserName() {
        return mSharedPreferences.getString(USER_NAME, null);
    }

    public String getPassword() {
        return mSharedPreferences.getString(PASSWORD, null);
    }

    public void setCredentials(String userName, String password) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(USER_NAME, userName);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public String getToken(){
        return mSharedPreferences.getString(TOKEN, null);
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

}
