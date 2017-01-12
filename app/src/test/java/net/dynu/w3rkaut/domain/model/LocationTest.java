package net.dynu.w3rkaut.domain.model;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class LocationTest {

    private Location newLocation;
    private static final long USER_ID = 0;
    private static final String USER_FIRST_NAME = "Pepito";
    private static final String USER_LAST_NAME = "Perez";
    private static final String POSTED_AT = "00:00";
    private static final Double DISTANCE = 10.1;
    private static final Integer PARTICIPANTS = 2;

    @Test
    public void isNewUserNotNullWhenIsCreated() {
        newLocation = new Location(USER_ID, USER_FIRST_NAME, USER_LAST_NAME,
                POSTED_AT, DISTANCE, PARTICIPANTS);
        assertThat(newLocation, is(notNullValue()));

        newLocation = new Location(USER_ID, PARTICIPANTS);
        assertThat(newLocation, is(notNullValue()));
    }

    @Test
    public void settersAndGettersWorkFine() {
        newLocation = new Location();
        newLocation.setUserId(USER_ID);
        newLocation.setUserFirstName(USER_FIRST_NAME);
        newLocation.setUserLastName(USER_LAST_NAME);
        newLocation.setPostedAt(POSTED_AT);
        newLocation.setDistance(DISTANCE);
        newLocation.setParticipants(PARTICIPANTS);

        assertEquals(USER_ID, newLocation.getUserId());
        assertEquals(USER_FIRST_NAME, newLocation.getUserFirstName());
        assertEquals(USER_LAST_NAME, newLocation.getUserLastName());
        assertEquals(POSTED_AT, newLocation.getPostedAt());
        assertEquals(DISTANCE, newLocation.getDistance());
        assertEquals(PARTICIPANTS, newLocation.getParticipants());
    }
}
