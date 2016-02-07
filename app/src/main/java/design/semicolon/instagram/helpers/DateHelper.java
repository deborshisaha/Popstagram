package design.semicolon.instagram.helpers;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dsaha on 2/6/16.
 */
public class DateHelper {

    private static final long SECONDS_IN_A_MINUTE = 60;
    private static final long SECONDS_IN_AN_HOUR = SECONDS_IN_A_MINUTE * 60;
    private static final long SECONDS_IN_A_DAY = SECONDS_IN_AN_HOUR * 24;
    private static final long SECONDS_IN_A_WEEK = SECONDS_IN_A_DAY * 7;

    public static String getInstagramStyleRelativeTiming(long timeStamp) {
        long currentTimeStamp = System.currentTimeMillis()/1000;
        long numberOfSeconds = (currentTimeStamp - timeStamp);

        String relativeString = new String();

        if (numberOfSeconds < 10) {
            return "Just now";
        } else if (numberOfSeconds < SECONDS_IN_A_MINUTE) {
            relativeString = numberOfSeconds +"s";
        } else if (numberOfSeconds > SECONDS_IN_A_MINUTE && numberOfSeconds < SECONDS_IN_AN_HOUR) {
            relativeString = (numberOfSeconds/SECONDS_IN_A_MINUTE) +"m";
        } else if (numberOfSeconds > SECONDS_IN_AN_HOUR && numberOfSeconds < SECONDS_IN_A_DAY) {
            relativeString = (numberOfSeconds/SECONDS_IN_AN_HOUR) +"h";
        } else if (numberOfSeconds > SECONDS_IN_A_DAY && numberOfSeconds < SECONDS_IN_A_WEEK) {
            relativeString = (numberOfSeconds/SECONDS_IN_A_DAY) +"d";
        } else if (numberOfSeconds > SECONDS_IN_A_WEEK) {
            relativeString = (numberOfSeconds/SECONDS_IN_A_WEEK) +"w";
        }

        return relativeString;
    }

}
