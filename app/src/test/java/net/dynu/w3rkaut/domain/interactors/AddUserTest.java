package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.impl.AddUserInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.threading.TestMainThread;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;


public class AddUserTest {


    private MainThread mainThread;
    @Mocked Executor mExecutor;
    @Mocked UserRepository mockedUserRepository;
    @Mocked AddUserInteractor.Callback mockedAdduserCallback;

    @Before
    public void setup() {
        mainThread = new TestMainThread();
    }

    @After
    public void teardown() {
        mainThread = null;
    }

    @Test
    public void isUserInsertionExecutedOneTime(@Injectable final User user) {

        new Expectations() {{
            mockedUserRepository.insert(user); times=1;
        }};

        AddUserInteractorImpl interactor = new AddUserInteractorImpl
                (mExecutor, mainThread, mockedUserRepository,
                        mockedAdduserCallback, user);
        interactor.run();

    }
}
