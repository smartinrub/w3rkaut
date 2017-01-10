package net.dynu.w3rkaut.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CurrentTime {

    public static Date getNow() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();

    }

    public static String formatTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ITALY);
        return dateFormat.format(date);
    }
}
