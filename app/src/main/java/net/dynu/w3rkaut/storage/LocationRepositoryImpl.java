package net.dynu.w3rkaut.storage;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.RestClient;
import net.dynu.w3rkaut.network.VolleySingleton;
import net.dynu.w3rkaut.network.services.SyncService;
import net.dynu.w3rkaut.network.converters.RESTLocationConverter;
import net.dynu.w3rkaut.network.model.RESTLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Here is where all the REST queries for the location POJO are done
 *
 * @author Sergio Martin Rubio
 */
public class LocationRepositoryImpl implements LocationRepository, com.android.volley.Response.ErrorListener, com.android.volley.Response.Listener<String> {

    public static final String REST_API_URL = "https://w3rkaut.dynu" +
            ".net/android/php/";

    private Context context;
    private String message = "";
    private List<RESTLocation> locations;

    public LocationRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public String insert(long id, Double latitude, Double
            longitude, String timeRemaining, String postedAt) {
        RESTLocationConverter converter = new RESTLocationConverter();
        RESTLocation restLocation = converter.convertToRestModel(id,
                latitude, longitude, timeRemaining,  postedAt);

        SyncService syncService =
                RestClient.getApiService();
        Call<String> call = syncService.insertLocation(
                restLocation.getUserId(),
                restLocation.getLatitude(),
                restLocation.getLongitude(),
                restLocation.getTimeRemaining(),
                restLocation.getPostedAt());

        try {
            Response<String> response = call.execute();
            message = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

//    @Override
//    public List<RESTLocation> getAll() {
//        List<RESTLocation> locations = new ArrayList<>();
//        SyncService syncService = RestClient.getApiService();
//        Call<List<RESTLocation>> call = syncService.getAllLocations();
//
//        try {
//            Response<List<RESTLocation>> response = call.execute();
//            locations = response.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return locations;
//    }

    @Override
    public List<RESTLocation> getAll() {
        locations = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                REST_API_URL + "locations.php", this, this);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue
                (stringRequest);
        return locations;
    }

    @Override
    public String delete(long id) {

        SyncService syncService = RestClient.getApiService();
        Call<String> call = syncService.deleteLocation(id);

        try {
            Response<String> response = call.execute();
            message = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long userId = jsonObject.getInt("user_id");
                String firstName = jsonObject.getString("first_name");
                String lastName = jsonObject.getString("last_name");
                double lat = Double.parseDouble(jsonObject.getString("latitude"));
                double lng = Double.parseDouble(jsonObject.getString("longitude"));
                String timeRemaining = jsonObject.getString("time_remaining");
                String postedAt = jsonObject.getString("posted_at");
                RESTLocation location = new RESTLocation(userId,
                        firstName, lastName, lat, lng, timeRemaining,
                        postedAt);
                locations.add(location);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Timber.e("error: " + e.getMessage());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
