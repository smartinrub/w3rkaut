package net.dynu.w3rkaut.presentation.presenters.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.DeleteLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.DeleteUserInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteUserInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.presentation.presenters.MainPresenter;
import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;


public class MainPresenterImpl extends AbstractPresenter implements
        MainPresenter, AddLocationInteractor.Callback, DeleteUserInteractor
        .Callback, DeleteLocationInteractor.Callback {

    private LocationRepository locationRepository;
    private UserRepository userRepository;
    private MainPresenter.View view;
    private SharedPreferencesManager sharedPreferencesManager;

    public MainPresenterImpl(Executor executor, MainThread mainThread, View view,
                             LocationRepository locationRepository,
                             UserRepository userRepository,
                             SharedPreferencesManager sharedPreferencesManager) {
        super(executor, mainThread);
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.view = view;
        this.sharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void addLocation(Double latitude, Double longitude, String time) {
        AddLocationInteractor addLocationInteractor = new AddLocationInteractorImpl(
                mExecutor,
                mMainThread,
                locationRepository,
                this,
                sharedPreferencesManager.getValue(),
                latitude,
                longitude,
                1,
                time
        );
        addLocationInteractor.execute();
    }

    @Override
    public void deleteUser(long userId) {
        DeleteUserInteractor deleteUserInteractor = new
                DeleteUserInteractorImpl(
                mExecutor,
                mMainThread,
                userRepository,
                this,
                userId);
        deleteUserInteractor.execute();
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
        DeleteLocationInteractor deleteLocationInteractor = new
                DeleteLocationInteractorImpl(mExecutor, mMainThread,
                locationRepository, this, userId);
        deleteLocationInteractor.execute();
    }

    @Override
    public void onLocationDeleted(String response) {
        view.onLocationDeleted(response);
    }
}
