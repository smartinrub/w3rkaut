package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;

public interface AddLocationInteractor extends Interactor {

    interface Callback {
        void onLocationAdded(String response);
    }
}
