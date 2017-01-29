package net.dynu.w3rkaut.presentation.converter;

import net.dynu.w3rkaut.R;

/**
 * This class return the following date format
 * "Publicado a las 00:00 el dia 0"
 *
 * @author Sergio Martin Rubio
 */
public class DateTimeConverter {

    public static String convert(String dateTime) {
        return R.string.posted_at +
                dateTime.substring(11, 13) +
                ":" +
                dateTime.substring(14, 16) +
                R.string.on +
                dateTime.substring(8, 10);
    }
}
