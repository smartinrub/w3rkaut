package net.dynu.w3rkaut.domain.respository;

import net.dynu.w3rkaut.domain.model.User;

/**
 * Created by sergio on 08/01/2017.
 */

public interface UserRepository {
    void insert(User user);
    void saveId(long id);
}
