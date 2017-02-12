package net.dynu.w3rkaut.presentation.presenters.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.DeleteLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.DeleteUserInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteUserInteractorImpl;

import net.dynu.w3rkaut.presentation.presenters.MainPresenter;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the actions which occur in the main activity
 *
 * @author Sergio Martin Rubio
 */
public class MainPresenterImpl implements
        MainPresenter, AddLocationInteractor.Callback, DeleteUserInteractor
        .Callback, DeleteLocationInteractor.Callback {

    private MainPresenter.View view;

    private Context context;

    public MainPresenterImpl(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void addLocation(long userId, Double latitude, Double longitude,
                            String timeRemaining, String postedAt) {
        AddLocationInteractor interactor = new AddLocationInteractorImpl();
        interactor.addLoction(userId, latitude, longitude,
                timeRemaining, postedAt, this, context);
    }

    @Override
    public void deleteUser(long userId) {
        DeleteUserInteractor interactor = new DeleteUserInteractorImpl();
        interactor.deleteUser(userId, this, context);
    }

    @Override
    public void onLocationAdded(String response) {
        view.onLocationAdded(response);
    }

    @Override
    public void onUserDeleted(String response) {
        view.onUserDeleted(response);
    }

    @Override
    public void deleteLocation(long userId) {
        DeleteLocationInteractor interactor = new DeleteLocationInteractorImpl();
        interactor.deleteLocation(userId, this, context);
    }

    @Override
    public void onLocationDeleted(String response) {
        view.onLocationDeleted(response);
    }
}
