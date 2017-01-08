package net.dynu.w3rkaut.domain;

import net.dynu.w3rkaut.domain.executor.ThreadExecutor;
import net.dynu.w3rkaut.domain.interactors.LoginInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.LoginInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by sergio on 08/01/2017.
 */

public class LoginTest {

    private ThreadExecutor threadExecutor;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private LoginInteractor.Callback mockedCallback;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        threadExecutor = new ThreadExecutor();
    }

    @Test
    public void isUserInsertionExecutedOneTime() {
        User newUser = new User(1, "a@a.com", "Pepito", "Perez");

        LoginInteractorImpl interactor = new LoginInteractorImpl(mockedCallback,
                mockedUserRepository, threadExecutor);
        interactor.login(newUser);
        Mockito.verify(mockedUserRepository).insert(newUser);
        Mockito.verifyNoMoreInteractions(mockedUserRepository);
    }
}
