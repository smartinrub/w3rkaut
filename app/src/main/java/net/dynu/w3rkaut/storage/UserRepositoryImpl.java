package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.network.RestClient;
import net.dynu.w3rkaut.network.services.SyncService;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Here is where all the REST queries for the user POJO are done
 *
 * @author Sergio Martin Rubio
 */
public class UserRepositoryImpl implements UserRepository {

    private Context context;

    public UserRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void insert(User user) {
        SyncService syncService = RestClient.getApiService();
        Call<Void> call = syncService.insertUser(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName());

        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveId(long id) {
        SharedPreferencesManager.getInstance(context).setValue(id);
    }

    @Override
    public String delete(long id) {
        String message = "";
        SyncService syncService = RestClient.getApiService();
        Call<String> call = syncService.deleteUser(id);

        try {
            Response<String> response = call.execute();
            message = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
