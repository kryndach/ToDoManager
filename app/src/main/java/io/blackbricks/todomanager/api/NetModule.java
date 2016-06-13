package io.blackbricks.todomanager.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.blackbricks.todomanager.api.service.AuthService;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yegorkryndach on 10/06/16.
 */

@Module
public class NetModule {

    public static final String AUTH_TOKEN = "auth_token";

    private String mBaseUrl;
    private String mAppKey;

    public NetModule(String baseUrl, String appKey) {
        this.mBaseUrl = baseUrl;
        this.mAppKey = appKey;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Named(AUTH_TOKEN)
    String authToken(SharedPreferences sharedPreferences) {
        return null;
    }

    @Provides
    Retrofit provideRetrofit(@Named(AUTH_TOKEN) String authToken) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new AuthInterceptor(mAppKey, authToken))
                .build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    AuthService provideAuthService(Retrofit retrofit){
        return retrofit.create(AuthService.class);
    }

}
