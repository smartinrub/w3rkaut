package net.dynu.w3rkaut.presentation.presenters.base;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;

/**
 * Created by sergio on 07/01/2017.
 */
public abstract class AbstractPresenter {
    protected Executor   mExecutor;
    protected MainThread mMainThread;

    public AbstractPresenter(Executor executor, MainThread mainThread) {
        mExecutor = executor;
        mMainThread = mainThread;
    }
}
