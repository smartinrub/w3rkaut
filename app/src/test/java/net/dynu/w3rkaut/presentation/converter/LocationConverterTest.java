package net.dynu.w3rkaut.presentation.converter;


import com.google.android.gms.maps.model.LatLng;

import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LocationConverterTest {

    @Test
    public void isConvertRESTLocationToLocationReturnsTheRightPojoList() {

        List<RESTLocation> restLocationList = new ArrayList<>();
        restLocationList.add(new RESTLocation(0, "name", "apellido", 0.0, 0.0, "00:00", "00:00"));
        LatLng latLng = new LatLng(0.0, 0.0);

        Assert.assertThat(LocationConverter.convertRESTLocationToLocation(restLocationList, latLng).get(0), is(instanceOf(Location.class)));
    }
}
