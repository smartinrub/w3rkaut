package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.threading.TestMainThread;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;

public class GetAllLocationsTest {

    private MainThread mainThread;
    @Mocked Executor mExecutor;
    @Mocked LocationRepository mockeedLocationRepository;
    @Mocked GetAllLocationsInteractor.Callback
            mockedGetAllLcoationInteractorCallback;
    @Mocked SaveUserIdInteractor.Callback mockedSaveUserIdCallback;

    @Before
    public void setup() {
        mainThread = new TestMainThread();
    }

    @After
    public void teardown() {
        mainThread = null;
    }

    @Test
    public void isGetAllLocationsExecutedOneTime() {
        new Expectations() {{
            mockeedLocationRepository.getAll(); times=1;
        }};

        GetAllLocationsInteractorImpl interactor = new
                GetAllLocationsInteractorImpl(mExecutor, mainThread,
                mockeedLocationRepository,
                mockedGetAllLcoationInteractorCallback);
        interactor.run();
    }

    @Test
    public void isGetAllLocationsReturnList() {

        assertThat(mockeedLocationRepository.getAll(), instanceOf(java.util
                .List.class));
    }
}
