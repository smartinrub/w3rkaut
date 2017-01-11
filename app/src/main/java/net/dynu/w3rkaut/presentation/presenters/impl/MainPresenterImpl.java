package net.dynu.w3rkaut.presentation.presenters.impl;

import com.google.android.gms.maps.model.LatLng;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.presentation.presenters.MainPresenter;
import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;

import java.util.TimerTask;

import timber.log.Timber;


public class MainPresenterImpl extends AbstractPresenter implements
        MainPresenter, AddLocationInteractor.Callback {

    private LocationRepository locationRepository;
    private MainPresenter.View view;
    private SharedPreferencesManager sharedPreferencesManager;

    public MainPresenterImpl(Executor executor, MainThread mainThread, View view,
                             LocationRepository locationRepository,
                             SharedPreferencesManager sharedPreferencesManager) {
        super(executor, mainThread);
        this.locationRepository = locationRepository;
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
    public void onLocationAdded(String response) {
        view.onLocationAdded(response);
    }
}
