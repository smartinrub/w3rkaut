package net.dynu.w3rkaut.presentation.converter;

public class DateTimeConverter {

    public static String convert(String dateTime) {
        return  "Publicado a las " +
                dateTime.substring(11, 13) +
                ":" +
                dateTime.substring(14, 16) +
                " el dia " +
                dateTime.substring(8, 10);
    }
}
