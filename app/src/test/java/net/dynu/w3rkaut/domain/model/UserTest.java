package net.dynu.w3rkaut.domain.model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private static final long ID = 1;
    private static final String EMAIL = "a@a.com";
    private static final String FIRST_NAME = "pepito";
    private static final String LAST_NAME = "perez";
    private User user;

    @Before
    public void setup() {
        user = new User(ID, EMAIL, FIRST_NAME, LAST_NAME);
    }

    @After
    public void teardown() {
        user = null;
    }

    @Test
    public void isNewUserCreated() {
        assertNotNull(user);
    }

    @Test
    public void isIDcorrect() {
        assertEquals(1,user.getUserId());
    }

    @Test
    public void isEmailcorrect() {
        assertEquals("a@a.com",user.getEmail());
    }

    @Test
    public void isFirstNamecorrect() {
        assertEquals("pepito",user.getFirstName());
    }

    @Test
    public void isLastNamecorrect() {
        assertEquals("perez",user.getLastName());
    }

    @Test
    public void isSetIdWorkingProperly() {
        user.setUserId(2);
        assertEquals(2, user.getUserId());
    }
}
