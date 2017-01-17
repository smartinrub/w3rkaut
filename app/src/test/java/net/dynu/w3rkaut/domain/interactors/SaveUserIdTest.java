package net.dynu.w3rkaut.domain.interactors;


import net.dynu.w3rkaut.domain.interactors.impl.SaveUserIdInteractorImpl;
import net.dynu.w3rkaut.domain.respository.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;


public class SaveUserIdTest {

//    private static final long USER_ID = 1;
//
//    private MainThread mainThread;
//    @Mocked
//    Executor mExecutor;
//    @Mocked
//    UserRepository mockedUserRepository;
//    @Mocked SaveUserIdInteractor.Callback mockedSaveUserIdCallback;
//
//    @Before
//    public void setup() {
//        mainThread = new TestMainThread();
//    }
//
//    @After
//    public void teardown() {
//        mainThread = null;
//    }
//
//    @Test
//    public void isUserIdSavedExecutedOneTime() {
//
//        new Expectations() {{
//            mockedUserRepository.saveId(USER_ID); times=1;
//        }};
//
//        SaveUserIdInteractorImpl interactor = new SaveUserIdInteractorImpl
//                (mExecutor, mainThread, mockedUserRepository,
//                        mockedSaveUserIdCallback, USER_ID);
//        interactor.run();
//
//    }
}
