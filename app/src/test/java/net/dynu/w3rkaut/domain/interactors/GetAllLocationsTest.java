package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import java.util.ArrayList;

import mockit.Mocked;

public class GetAllLocationsTest {

    private MainThread mainThread;
    @Mocked private Executor mExecutor;
    @Mocked private LocationRepository locationRepository;
    @Mocked private GetAllLocationsInteractor.Callback
            mockedGetAllLcoationInteractorCallback;
    @Mocked private SaveUserIdInteractor.Callback mockedSaveUserIdCallback;

    @Before
    public void setup() {
        mainThread = new TestMainThread();
    }

    @After
    public void teardown() {
        mainThread = null;
    }

    @Test
    public void isGetAllLocationsReturnAListOfLocation() {
//        List<RESTLocation> locations = new ArrayList<>();
//        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
//        when(locationRepository.getAll()).thenReturn(locations);
//
//        GetAllLocationsInteractorImpl interactor = new
//                GetAllLocationsInteractorImpl(mExecutor, mainThread,
//                locationRepository, mockedGetAllLcoationInteractorCallback);
//        interactor.run();
//
//        Mockito.verify(locationRepository).getAll();
//        Mockito.verifyNoMoreInteractions(locationRepository);
//        Mockito.verify(mockedGetAllLcoationInteractorCallback)
//                .onLocationsRetrieved(locations);
    }

    @Test
    public void isReturningListContainingTheRightNumberOfLocations() {
//        List<RESTLocation> locations = new ArrayList<>();
//        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
//        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
//        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
//        when(locationRepository.getAll()).thenReturn(locations);
//
//        List<RESTLocation> locationResponse = locationRepository.getAll();
//
//        assertThat(locationResponse.size(), is(equalTo(3)));
    }

}
