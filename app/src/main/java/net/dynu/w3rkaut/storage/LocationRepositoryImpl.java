package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.RestClient;
import net.dynu.w3rkaut.network.services.SyncService;
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
    private String message = "";

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
            message = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
