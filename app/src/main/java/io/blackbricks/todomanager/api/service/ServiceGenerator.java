package io.blackbricks.todomanager.api.service;

import android.preference.Preference;
import android.preference.PreferenceManager;

import java.io.IOException;

import io.blackbricks.todomanager.ToDoManagerApp;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public class ServiceGenerator {
    public static final String API_BASE_URL = "http://52.40.208.128:80";
    private static final String APP_KEY = "a8536db6-2230-4a4d-9086-0e500fcd760f";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("app_key", APP_KEY)
                        .method(original.method(), original.body());

                if (authToken != null) {
                    requestBuilder.header("session_key", authToken);
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
