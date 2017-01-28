package net.dynu.w3rkaut.network.converters;


import net.dynu.w3rkaut.network.model.RESTLocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

public class RestLocationConverterTest {

    private RESTLocationConverter restLocationConverter;

    @Before
    public void setup(){
        restLocationConverter = new RESTLocationConverter();

    }

    @Test
    public void isRestLocationReturnTheRightPojo() {
        Assert.assertThat(restLocationConverter.convertToRestModel(0, 0.0, 0.0, "00:00", "00:00"),
                is(instanceOf(RESTLocation.class)));
    }
}
