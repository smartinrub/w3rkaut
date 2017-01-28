package net.dynu.w3rkaut.domain.respository;

import net.dynu.w3rkaut.domain.model.User;

public interface UserRepository {

    void insert(User user);

    void saveId(long id);

    String delete(long id);
}
