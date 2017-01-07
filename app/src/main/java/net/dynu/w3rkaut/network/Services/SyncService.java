package net.dynu.w3rkaut.network.Services;

import net.dynu.w3rkaut.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * A REST interface describing how data will be synced with the backend.
 * <p/>
 */
public interface SyncService {

    /**
     * This endpoint will be used to send new users created on this device.
     */
    @FormUrlEncoded
    @POST("login.php")
    Call<Void> insertUser(
            @Field("id") long id,
            @Field("email") String email,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName);

    @FormUrlEncoded
    @POST("insert_location.php")
    Call<String> insertLocation(
            @Field("user_id") String userId,
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude,
            @Field("participants") Integer participants,
            @Field("posted_at") String postedAt);

    @GET("locations.php")
    Call<List<User>> getLocationUsers();

    @FormUrlEncoded
    @POST("delete_location.php")
    Call<String> deleteLocation(
            @Field("user_id") String userId);
}
