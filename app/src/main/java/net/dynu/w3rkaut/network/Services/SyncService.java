package net.dynu.w3rkaut.network.Services;

import net.dynu.w3rkaut.network.model.RESTLocation;

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

    @FormUrlEncoded
    @POST("login.php")
    Call<Void> insertUser(
            @Field("id") long id,
            @Field("email") String email,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName);

    @FormUrlEncoded
    @POST("delete_user.php")
    Call<String> deleteUser(@Field("user_id") long userId);

    @FormUrlEncoded
    @POST("insert_location.php")
    Call<String> insertLocation(
            @Field("user_id") long userId,
            @Field("latitude") Double latitude,
            @Field("longitude") Double longitude,
            @Field("participants") Integer participants,
            @Field("posted_at") String postedAt);

    @GET("locations.php")
    Call<List<RESTLocation>> getAllLocations();

    @FormUrlEncoded
    @POST("delete_location.php")
    Call<String> deleteLocation(
            @Field("user_id") long userId);
}
