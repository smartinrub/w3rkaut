package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.AddUserInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.DeleteUserInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;


public class DeleteUserTest {
    private static final long USER_ID = 1234567891;

    private MainThread mainThread;
    @Mocked
    Executor mExecutor;
    @Mocked
    UserRepository mockedUserRepository;
    @Mocked DeleteUserInteractor.Callback mockedDeleteUserCallback;

    @Before
    public void setup() {
        mainThread = new TestMainThread();
    }

    @After
    public void teardown() {
        mainThread = null;
    }

    @Test
    public void isUserInsertionExecutedOneTime() {

        new Expectations() {{
            mockedUserRepository.delete(USER_ID); times=1;
        }};

        DeleteUserInteractorImpl interactor = new DeleteUserInteractorImpl
                (mExecutor, mainThread, mockedUserRepository,
                        mockedDeleteUserCallback, USER_ID);
        interactor.run();

    }
}
