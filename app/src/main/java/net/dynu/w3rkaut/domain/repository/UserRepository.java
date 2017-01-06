package net.dynu.w3rkaut.domain.repository;

import net.dynu.w3rkaut.domain.model.User;

/**
 * Created by sergio on 06/01/2017.
 */

public interface UserRepository {

    void insert(User user);

    User get();
}
