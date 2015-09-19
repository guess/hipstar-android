package me.hipstar.android.network;

import me.hipstar.android.models.UserResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface HypeMachineApi {
    String BASE_URL = "https://api.hypem.com/v2";

    @FormUrlEncoded
    @POST("/get_token")
    void getToken(@Field("username") String username, @Field("password") String password,
                  @Field("device_id") String deviceId, Callback<UserResponse> callback);
}
