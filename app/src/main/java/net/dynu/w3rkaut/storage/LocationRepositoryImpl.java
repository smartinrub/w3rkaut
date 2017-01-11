package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.RestClient;
import net.dynu.w3rkaut.network.Services.SyncService;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by sergio on 09/01/2017.
 */

public class LocationRepositoryImpl implements LocationRepository {

    private Context context;
    private List<RESTLocation> locations;
    private String message;

    public LocationRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<RESTLocation> getAll() {
        locations = new ArrayList<>();
        SyncService syncService = RestClient.getApiService();
        Call<List<RESTLocation>> response = syncService.getAllLocations();

        response.enqueue(new Callback<List<RESTLocation>>() {
            @Override
            public void onResponse(Call<List<RESTLocation>> call, Response<List<RESTLocation>> response) {
                if(response.isSuccessful()) {
                    locations = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<RESTLocation>> call, Throwable t) {

            }
        });
        return locations;
    }

    @Override
    public String insert(RESTLocation location) {
        SyncService syncService = RestClient.getApiService();
        Call<String> response = syncService.insertLocation(
                location.getUserId(),
                location.getLatitude(),
                location.getLongitude(),
                location.getParticipants(),
                location.getPostedAt());
        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().indexOf("user already has a location") > 0) {
                    message = "Ya has publicado una localización";
                    Timber.e(message);


                } else {
                    message = "Posicion añadida";
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        Timber.e(message + "hola");
        return message;
    }
}
