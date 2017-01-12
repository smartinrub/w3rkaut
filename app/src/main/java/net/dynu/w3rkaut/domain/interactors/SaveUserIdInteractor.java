package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;

public interface SaveUserIdInteractor extends Interactor {
    interface Callback {
        void onUserIdSaved();
    }
}
