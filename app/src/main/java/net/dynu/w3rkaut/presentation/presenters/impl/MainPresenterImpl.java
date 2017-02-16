package net.dynu.w3rkaut.presentation.presenters.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.domain.interactors.interfaces.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.interfaces.DeleteLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.interfaces.DeleteUserInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteUserInteractorImpl;

import net.dynu.w3rkaut.presentation.presenters.interfaces.MainPresenter;
import net.dynu.w3rkaut.services.interfaces.LocationService;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the actions which occur in the main activity
 *
 * @author Sergio Martin Rubio
 */
public class MainPresenterImpl implements MainPresenter,
        DeleteUserInteractor.Callback, LocationService.VolleyCallback {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();

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
        interactor.addLocation(userId, latitude, longitude,
                timeRemaining, postedAt, this, context);
    }

    @Override
    public void deleteUser(long userId) {
        DeleteUserInteractor interactor = new DeleteUserInteractorImpl();
        interactor.deleteUser(userId, this, context);
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
    public void notifySuccess(String response) {
        view.onLocationAdded(response);
        view.onLocationDeleted(response);
    }

    @Override
    public void notifyError(VolleyError error) {
        Log.e(TAG, String.valueOf(error));
    }
}
