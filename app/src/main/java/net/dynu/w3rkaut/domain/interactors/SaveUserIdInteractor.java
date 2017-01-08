package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;

/**
 * Created by sergio on 08/01/2017.
 */

public interface SaveUserIdInteractor extends Interactor {
    interface Callback {
        void onUserIdSaved();
    }
}
