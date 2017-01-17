package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.network.RestClientModule;
import net.dynu.w3rkaut.network.Services.SyncService;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;

import java.io.IOException;

import retrofit2.Call;

public class UserRepositoryImpl implements UserRepository {

    private Context context;

    public UserRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void insert(User user) {
        SyncService syncService = RestClientModule.provideApiService();
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
}
