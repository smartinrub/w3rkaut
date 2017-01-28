package net.dynu.w3rkaut.presentation.converter;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;

public class DateTimeConverterTest {

    @Test
    public void isConvertReturnTheRightString() {
        Assert.assertThat(DateTimeConverter.convert("2017-01-28 20:34:15"),
                containsString("Publicado a las"));
        Assert.assertThat(DateTimeConverter.convert("2017-01-28 20:34:15"),
                containsString(":"));
        Assert.assertThat(DateTimeConverter.convert("2017-01-28 20:34:15"),
                containsString("el dia"));
    }
}
