package net.dynu.w3rkaut.domain.executor;

import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;

/**
 * This executor is responsible for running interactors on background threads
 */

public interface Executor {
    void execute(final AbstractInteractor interactor);
}
