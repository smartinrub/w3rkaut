package net.dynu.w3rkaut.storage;

import android.content.Context;

import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.repository.UserRepository;

/**
 * Created by sergio on 07/01/2017.
 */

public class UserRespositoryImpl implements UserRepository {
    private Context context;

    public UserRespositoryImpl(Context context){
        this.context = context;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public User get() {
        return null;
    }
}
