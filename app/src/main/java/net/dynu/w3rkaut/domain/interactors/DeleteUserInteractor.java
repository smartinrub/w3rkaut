package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;

public interface DeleteUserInteractor extends Interactor {

    interface Callback {
        void onUserDeleted(String response);
    }
}
