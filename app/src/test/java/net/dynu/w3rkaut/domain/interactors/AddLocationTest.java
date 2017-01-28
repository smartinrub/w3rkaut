package net.dynu.w3rkaut.domain.interactors;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;


public class AddLocationTest {
    private static final long ID = 1;
    private static final Double LAT = 0.0;
    private static final Double LNG = 0.0;
    private static final String TIME_REMAINING = "00:00";
    private static final String POSTED_AT = "00:00";

    private MainThread mainThread;
    @Mocked
    Executor mExecutor;
    @Mocked
    LocationRepository mockedLocationRepository;
    @Mocked AddLocationInteractor.Callback mockedCallback;

    @Before
    public void setup() {
        mainThread = new TestMainThread();
    }


    @Test
    public void isAddLocationExecutedOneTime() {

        new Expectations() {{
            mockedLocationRepository.insert(ID, LAT, LNG,
                    TIME_REMAINING, POSTED_AT); times=1;
        }};

        AddLocationInteractorImpl interactor = new AddLocationInteractorImpl
                (mExecutor, mainThread, mockedLocationRepository,
                        mockedCallback, ID, LAT, LNG,TIME_REMAINING, POSTED_AT);
        interactor.run();
    }
}
