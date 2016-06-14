package io.blackbricks.todomanager.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yegorkryndach on 14/06/16.
 */
public class LoginResponse {
    @SerializedName("session_key")
    String token;

    public String getToken() {
        return token;
    }
}
