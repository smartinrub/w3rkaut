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

import mockit.Expectations;
import mockit.Mocked;

public class GetAllLocationsTest {

    private MainThread mainThread;
    @Mocked private Executor mExecutor;
    @Mocked private LocationRepository mockeedLocationRepository;
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

        new Expectations() {{
           mockeedLocationRepository.getAll();
        }};

        GetAllLocationsInteractorImpl interactor = new
                GetAllLocationsInteractorImpl(mExecutor, mainThread,
                mockeedLocationRepository,
                mockedGetAllLcoationInteractorCallback);
        interactor.run();
    }
}
