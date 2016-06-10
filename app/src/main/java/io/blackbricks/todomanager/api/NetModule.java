package io.blackbricks.todomanager.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yegorkryndach on 10/06/16.
 */

@Module
public class NetModule {

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
    @Singleton
    OkHttpClient.Builder provideOkHttpClient() {
        return new OkHttpClient.Builder();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient.Builder okHttpClientBuilder, SharedPreferences sharedPreferences) {
        OkHttpClient okHttpClient = okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header(ApiConstants.APP_KEY_HEADER, mAppKey)
                        .method(original.method(), original.body());

                if (authToken != null) {
                    requestBuilder.header(ApiConstants.SESSION_KEY_HEADER, authToken);
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }

}
