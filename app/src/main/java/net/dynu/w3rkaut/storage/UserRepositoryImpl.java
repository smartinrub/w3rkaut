package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.network.RestClient;
import net.dynu.w3rkaut.network.Services.SyncService;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Response;
import timber.log.Timber;

public class UserRepositoryImpl implements UserRepository {

    private Context context;

    public UserRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void insert(User user) {
        SyncService syncService = RestClient.getApiService();
        Call<Void> response = syncService.insertUser(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName());

        response.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Timber.e("Usuario guardado con exito");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Timber.e("Fallo al guardar usuario");

            }
        });
    }

    @Override
    public void saveId(long id) {
        SharedPreferencesManager.getInstance(context).setValue(id);
    }
}
