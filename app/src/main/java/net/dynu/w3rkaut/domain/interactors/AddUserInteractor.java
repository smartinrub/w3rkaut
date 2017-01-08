package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;
import net.dynu.w3rkaut.domain.model.User;

/**
 * Created by sergio on 06/01/2017.
 */

public interface AddUserInteractor extends Interactor {

    interface Callback {
        void onUserAdded();
    }

}
