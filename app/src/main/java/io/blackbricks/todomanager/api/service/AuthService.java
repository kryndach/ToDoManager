package io.blackbricks.todomanager.api.service;

import io.blackbricks.todomanager.api.response.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yegorkryndach on 09/06/16.
 */
public interface AuthService {

    @POST("api/v1/users/create")
    Observable<ResponseBody> create(
            @Query("username") String username,
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("api/v1/login")
    Observable<LoginResponse> login(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("api/v1/username/{username}")
    Observable<ResponseBody> checkUserName(
            @Path("username") String username
    );

    @GET("api/v1/email/{email}")
    Observable<ResponseBody> checkEmail(
            @Path("email") String email
    );
}
