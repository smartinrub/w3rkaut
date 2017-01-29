package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.RestClient;
import net.dynu.w3rkaut.network.Services.SyncService;
import net.dynu.w3rkaut.network.converters.RESTLocationConverter;
import net.dynu.w3rkaut.network.model.RESTLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Here is where all the REST queries for the location POJO are done
 *
 * @author Sergio Martin Rubio
 */
public class LocationRepositoryImpl implements LocationRepository {

    private Context context;
    private String message = String.valueOf(R.string.rest_error_message);

    public LocationRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public String insert(long id, Double latitude, Double
            longitude, String timeRemaining, String postedAt) {
        RESTLocationConverter converter = new RESTLocationConverter();
        RESTLocation restLocation = converter.convertToRestModel(id,
                latitude, longitude, timeRemaining,  postedAt);

        SyncService syncService = RestClient.getApiService();
        Call<String> call = syncService.insertLocation(
                restLocation.getUserId(),
                restLocation.getLatitude(),
                restLocation.getLongitude(),
                restLocation.getTimeRemaining(),
                restLocation.getPostedAt());

        try {
            Response<String> response = call.execute();
            if (response.body().indexOf("user already has a location") > 0) {
                message = context.getString(R.string.no_more_than_one_location_per_user);
            } else if(response.body().indexOf("successfully saved") > 0) {
                message = context.getString(R.string.location_added);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public List<RESTLocation> getAll() {
        List<RESTLocation> locations = new ArrayList<>();
        SyncService syncService = RestClient.getApiService();
        Call<List<RESTLocation>> call = syncService.getAllLocations();

        try {
            Response<List<RESTLocation>> response = call.execute();
            locations = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locations;
    }

    @Override
    public String delete(long id) {

        SyncService syncService = RestClient.getApiService();
        Call<String> call = syncService.deleteLocation(id);

        try {
            Response<String> response = call.execute();
            if(response.body().indexOf("user do not have a location") > 0){
                message = context.getString(R.string.you_did_not_publish_any_location_yet);
            }
            else if(response.body().indexOf("successfully deleted") > 0) {
                message = context.getString(R.string.location_deleted);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
