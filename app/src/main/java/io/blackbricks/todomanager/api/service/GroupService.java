package io.blackbricks.todomanager.api.service;

import java.util.List;

import io.blackbricks.todomanager.model.Group;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yegorkryndach on 13/06/16.
 */
public interface GroupService {
    @GET("api/v1/user/groups")
    Observable<List<Group>> getGroups();

    @POST("api/v1/user/group")
    Observable<Group> createGroup(
            @Body Group group
    );
}
