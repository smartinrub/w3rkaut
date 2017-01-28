package net.dynu.w3rkaut.storage;

import android.content.Context;

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

public class LocationRepositoryImpl implements LocationRepository {

    private Context context;
    private String message = "Lo sentimos, se ha producido un error, intentelo " +
            "de nuevo";

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
                message = "No puedes publicar más de una posición";
            } else if(response.body().indexOf("successfully saved") > 0) {
                message = "Posicion añadida";
            } else {
                message = "Lo sentimos, se ha producido un error, intentelo " +
                        "de nuevo";
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
                message = "No tiene ninguna posición publicada";
            }
            else if(response.body().indexOf("successfully deleted") > 0) {
                message = "Localización eliminada";
            } else {
                message = "No se ha podido eliminar la localización";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
