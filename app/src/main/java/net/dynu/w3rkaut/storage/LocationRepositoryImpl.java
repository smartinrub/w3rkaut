package net.dynu.w3rkaut.storage;

import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.RestClientModule;
import net.dynu.w3rkaut.network.Services.SyncService;
import net.dynu.w3rkaut.network.converters.RESTLocationConverter;
import net.dynu.w3rkaut.network.model.RESTLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LocationRepositoryImpl implements LocationRepository {

    @Override
    public String insert(long id, Integer participants, Double latitude, Double
            longitude, String postedAt) {
        Location location = new Location(id, participants);
        RESTLocationConverter converter = new RESTLocationConverter();
        RESTLocation restLocation = converter.convertToRestModel(location,
                latitude, longitude, postedAt);

        String message = "";
        SyncService syncService = RestClientModule.provideApiService();
        Call<String> call = syncService.insertLocation(
                restLocation.getUserId(),
                restLocation.getLatitude(),
                restLocation.getLongitude(),
                restLocation.getParticipants(),
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
        SyncService syncService = RestClientModule.provideApiService();
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
        String message = "";

        SyncService syncService = RestClientModule.provideApiService();
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
