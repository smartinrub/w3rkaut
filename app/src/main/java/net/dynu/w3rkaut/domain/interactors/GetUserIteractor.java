package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;
import net.dynu.w3rkaut.domain.model.User;

/**
 * Created by sergio on 06/01/2017.
 *
 * This interactor is responsible for retrieving the user info from Facebook
 * API.
 */

public interface GetUserIteractor extends Interactor {
    interface Callback {
        void onUserRetrieved(User user);
    }
}
