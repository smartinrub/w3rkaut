//package net.dynu.w3rkaut.presentation.presenters.impl;
//
//
//import com.google.android.gms.maps.model.LatLng;
//
//import net.dynu.w3rkaut.domain.executor.Executor;
//import net.dynu.w3rkaut.domain.executor.MainThread;
//import net.dynu.w3rkaut.domain.interactors.DeleteLocationInteractor;
//import net.dynu.w3rkaut.domain.interactors.GetAllLocationsInteractor;
//import net.dynu.w3rkaut.domain.interactors.impl.DeleteLocationInteractorImpl;
//import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
//import net.dynu.w3rkaut.domain.respository.LocationRepository;
//import net.dynu.w3rkaut.network.model.RESTLocation;
//import net.dynu.w3rkaut.presentation.Model.Location;
//import net.dynu.w3rkaut.presentation.converter.LocationConverter;
//import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
//import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;
//
//import java.util.List;
//
//import timber.log.Timber;
//
//
//public class LocationListPresenterImpl extends AbstractPresenter implements
//        LocationListPresenter, GetAllLocationsInteractor.Callback,
//        DeleteLocationInteractor.Callback {
//
//    private LocationRepository locationRepository;
//    private LocationListPresenter.View view;
//    private LatLng currentLatLng;
//
//    public LocationListPresenterImpl(Executor executor, MainThread
//            mainThread, View view, LocationRepository locationRepository,
//                                     LatLng currentLatLng) {
//        super(executor, mainThread);
//        this.view = view;
//        this.locationRepository = locationRepository;
//        this.currentLatLng = currentLatLng;
//    }
//
//    @Override
//    public void getAllLocations() {
//        GetAllLocationsInteractor interactor = new
//                GetAllLocationsInteractorImpl(mExecutor, mMainThread,
//                locationRepository, this);
//        interactor.execute();
//    }
//
//    @Override
//    public void onLocationsRetrieved(List<RESTLocation> locationList) {
//        List<Location> locations = LocationConverter
//                .convertRESTLocationToLocation(locationList, currentLatLng);
//
//        view.showLocations(locations);
//    }
//
//    @Override
//    public void deleteLocation(long userId) {
//        DeleteLocationInteractor deleteLocationInteractor = new
//                DeleteLocationInteractorImpl(mExecutor, mMainThread,
//                locationRepository, this, userId);
//        deleteLocationInteractor.execute();
//    }
//
//    @Override
//    public void onLocationDeleted(String response) {
//        view.onLocationDeleted(response);
//    }
//}
