package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;

public interface DeleteLocationInteractor extends Interactor {

    interface Callback {
        void onLocationDeleted(String response);
    }
}
