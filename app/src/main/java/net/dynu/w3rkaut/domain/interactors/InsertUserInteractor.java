package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;

/**
 * Created by sergio on 06/01/2017.
 *
 * This interactor is responsible for sending user info to the database
 */

public interface InsertUserInteractor extends Interactor {
    interface Callback {
        void onUserInserted();
    }
}
