package net.dynu.w3rkaut.domain.executor;

/**
 * This interface will define a class that will enable interactors to run certain operations on the main (UI) thread. For example,
 * if an interactor needs to show an object to the UI this can be used to make sure the show method is called on the UI
 * thread.
 */
public interface MainThread {
    void post(final Runnable runnable);
}