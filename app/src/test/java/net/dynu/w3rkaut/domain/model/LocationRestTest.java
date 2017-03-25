package net.dynu.w3rkaut.domain.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LocationRestTest {

    private static final long USER_ID = 1;
    private static final String USER_FIRST_NAME = "pepito";
    private static final String USER_LAST_NAME = "perez";
    private static final Double LATITUDE = 0.1;
    private static final Double LONGITUDE = 0.9;
    private static final String TIME_REMAINING = "00:29:09";
    private static final String POSTED_AT = "2017-03-25 17:11:34";
    private LocationRest locationRest;

    @Before
    public void setup() {
        locationRest = new LocationRest(
                USER_ID,
                USER_FIRST_NAME,
                USER_LAST_NAME,
                LATITUDE,
                LONGITUDE,
                TIME_REMAINING,
                POSTED_AT);
    }

    @After
    public void teardown() {
        locationRest = null;
    }

    @Test
    public void isLocationRestCreated() {
        assertThat(locationRest, is(notNullValue()));
    }

    @Test
    public void isConstructorWithGeoVariablesWorking() {
        LocationRest locationRest2 = new LocationRest(LATITUDE, LONGITUDE);
        assertThat(locationRest2, is(notNullValue()));
    }

    @Test
    public void isUserIdCorrect() {
        assertThat(locationRest.getUserId(), is(equalTo(1L)));
    }

    @Test
    public void isUserFirstNameCorrect() {
        assertThat(locationRest.getUserFirstName(), is(equalTo("pepito")));
    }

    @Test
    public void isUserLastNameCorrect() {
        assertThat(locationRest.getUserLastName(), is(equalTo("perez")));
    }

    @Test
    public void isUserLatitudeCorrect() {
        assertThat(locationRest.getLatitude(), is(equalTo(0.1)));
    }

    @Test
    public void isUserLongitudeCorrect() {
        assertThat(locationRest.getLongitude(), is(equalTo(0.9)));
    }

    @Test
    public void isUserTimeRemainingCorrect() {
        assertThat(locationRest.getTimeRemaining(), is(equalTo(TIME_REMAINING)));
    }

    @Test
    public void isUserPostedAtCorrect() {
        assertThat(locationRest.getPostedAt(), is(equalTo(POSTED_AT)));
    }


}
