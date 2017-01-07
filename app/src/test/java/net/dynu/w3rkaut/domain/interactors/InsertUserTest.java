package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.GetUserInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.InsertUserInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.repository.UserRepository;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by sergio on 07/01/2017.
 */

public class InsertUserTest {
    private MainThread mainThread;
    @Mock
    private Executor mockedExecutor;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private InsertUserInteractor.Callback mockedCallback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
    }

    @Test
    public void testGetUserCreatesNewUser() {
        InsertUserInteractorImpl interactor =
                new InsertUserInteractorImpl(mockedExecutor, mainThread,
                        mockedCallback, mockedUserRepository, 1, "a@a.com",
                        "Pepito", "Perez");
        interactor.run();

        Mockito.verify(mockedUserRepository).get();
        Mockito.verifyNoMoreInteractions(mockedUserRepository);
    }
}
