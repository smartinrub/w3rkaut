package net.dynu.w3rkaut.domain.interactors;


import net.dynu.w3rkaut.domain.interactors.impl.AddUserInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

import org.junit.Test;


import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;

public class AddUserTest {


    @Mocked UserRepository mockedUserRepository;
    @Mocked AddUserInteractor.Callback mockedAdduserCallback;

    @Test
    public void isUserInsertionExecutedOneTime(@Injectable final User user) {


        new Expectations() {{
            mockedUserRepository.insert(user); times=1;
        }};


        AddUserInteractorImpl interactor = new AddUserInteractorImpl
                (mockedUserRepository, mockedAdduserCallback);
        interactor.addUser(user);

    }
}
