package net.dynu.w3rkaut.domain.executor;

import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;

/**
 * This executor is responsible for running interactors on background threads.
 *
 * Created by sergio on 08/01/2017.
 */

public interface Executor {
    /**
     * This method should call the interactor's run method and thus start the interactor. This should be called
     * on a background thread as interactors might do lengthy operations.
     *
     * @param interactor The interactor to run.
     */
    void execute(final AbstractInteractor interactor);
}
