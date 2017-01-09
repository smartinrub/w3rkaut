package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.AddUserInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.SaveUserIdInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by sergio on 08/01/2017.
 */

public class LoginTest {

    private MainThread mainThread;
    @Mock private Executor mExecutor;
    @Mock private UserRepository mockedUserRepository;
    @Mock private AddUserInteractor.Callback mockedAdduserCallback;
    @Mock private SaveUserIdInteractor.Callback mockedSaveUserIdCallback;

    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainThread = new TestMainThread();
        user = new User(1, "a@a.com", "Pepito", "Perez");
    }

    @After
    public void teardown() {
        mainThread = null;
        user = null;
    }

    @Test
    public void isUserInsertionExecutedOneTime() {

        AddUserInteractorImpl interactor = new AddUserInteractorImpl
                (mExecutor, mainThread, mockedUserRepository,
                        mockedAdduserCallback, user);
        interactor.run();
        Mockito.verify(mockedUserRepository).insert(user);
        Mockito.verifyNoMoreInteractions(mockedUserRepository);
    }

    @Test
    public void isUserIdSaved() {
        SaveUserIdInteractorImpl interactor = new SaveUserIdInteractorImpl
                (mExecutor, mainThread, mockedUserRepository,
                        mockedSaveUserIdCallback, user.getUserId());
        interactor.run();
        Mockito.verify(mockedUserRepository).saveId(user.getUserId());
        Mockito.verifyNoMoreInteractions(mockedUserRepository);
    }
}
