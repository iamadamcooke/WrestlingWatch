package com.getpebble.WrestlingWatch;

/**
 * Created by adamcooke on 6/22/15.
 */
public class TimeHelpers {

    public static String millisToStringTime(long time) {
        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        return "" + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);
    }

}