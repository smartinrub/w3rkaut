package net.dynu.w3rkaut.domain.interactors;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteLocationInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;

public class DeleteLocationTest {

    private static final long ID = 1;

    private MainThread mainThread;
    @Mocked
    Executor mExecutor;
    @Mocked
    LocationRepository mockedLocationRepository;
    @Mocked
    DeleteLocationInteractor.Callback mockedCallback;

    @Before
    public void setup() {
        mainThread = new TestMainThread();
    }

    @After
    public void teardown() {
        mainThread = null;
    }

    @Test
    public void isAddUserExecutedOneTime() {

        new Expectations() {{
            mockedLocationRepository.delete(ID);
            times = 1;
        }};

        DeleteLocationInteractorImpl interactor = new DeleteLocationInteractorImpl(
                mExecutor, mainThread, mockedLocationRepository,
                mockedCallback, ID);
        interactor.run();

    }

}

