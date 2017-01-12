package net.dynu.w3rkaut.domain.model;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.hamcrest.CoreMatchers.*;


public class UserTest {

    private User newUser;
    private static final long ID = 0;
    private static final String EMAIL = "a@a.com";
    private static final String FIRST_NAME = "Pepito";
    private static final String LAST_NAME = "Perez";

    @Before
    public void setup() {

    }

    @Test
    public void isNewUserNotNullWhenIsCreated() {
        newUser = new User(ID, EMAIL, FIRST_NAME, LAST_NAME);
        assertNotNull(newUser);
        assertThat(newUser, is(notNullValue()));
    }

    @Test
    public void settersAndGettersWorkFine() {
        newUser = new User();
        newUser.setUserId(ID);
        newUser.setEmail(EMAIL);
        newUser.setFirstName(FIRST_NAME);
        newUser.setLastName(LAST_NAME);

        assertEquals(ID, newUser.getUserId());
        assertEquals(EMAIL, newUser.getEmail());
        assertEquals(FIRST_NAME, newUser.getFirstName());
        assertEquals(LAST_NAME, newUser.getLastName());
    }

}
