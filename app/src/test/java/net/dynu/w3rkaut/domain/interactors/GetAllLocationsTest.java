package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.threading.TestMainThread;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by sergio on 09/01/2017.
 */

public class GetAllLocationsTest {

    private MainThread mainThread;
    @Mock
    private Executor mExecutor;
    @Mock private LocationRepository locationRepository;
    @Mock private GetAllLocationsInteractor.Callback
            mockedGetAllLcoationInteractorCallback;
    @Mock private SaveUserIdInteractor.Callback mockedSaveUserIdCallback;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
    }

    @After
    public void teardown() {
        mainThread = null;
    }

    @Test
    public void isGetAllLocationsReturnAListOfLocation() {
        List<RESTLocation> locations = new ArrayList<>();
        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
        when(locationRepository.getAll()).thenReturn(locations);

        GetAllLocationsInteractorImpl interactor = new
                GetAllLocationsInteractorImpl(mExecutor, mainThread,
                locationRepository, mockedGetAllLcoationInteractorCallback);
        interactor.run();

        Mockito.verify(locationRepository).getAll();
        Mockito.verifyNoMoreInteractions(locationRepository);
        Mockito.verify(mockedGetAllLcoationInteractorCallback)
                .onLocationsRetrieved(locations);
    }

    @Test
    public void isReturningListContainingTheRightNumberOfLocations() {
        List<RESTLocation> locations = new ArrayList<>();
        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
        locations.add(new RESTLocation(1, 0.0, 1.1, "00:00", 1));
        when(locationRepository.getAll()).thenReturn(locations);

        List<RESTLocation> locationResponse = locationRepository.getAll();

        assertThat(locationResponse.size(), is(equalTo(3)));
    }

}
