package io.blackbricks.todomanager.api;

import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yegorkryndach on 13/06/16.
 */
public class AuthInterceptor implements Interceptor {

    private String mAppKey;
    private String mAuthToken;

    public AuthInterceptor(String mAppKey, String mAuthToken) {
        this.mAppKey = mAppKey;
        this.mAuthToken = mAuthToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header(ApiConstants.APP_KEY_HEADER, mAppKey)
                .method(original.method(), original.body());

        if (mAuthToken != null) {
            requestBuilder.header(ApiConstants.SESSION_KEY_HEADER, mAuthToken);
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
