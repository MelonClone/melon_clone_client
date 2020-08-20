package com.devgd.melonclone.utils.view;

public class TimeFormatter {
    public static String millisecondToClock(long millisecond) {
        int hour = 0;
        int minute = 0;
        int second = 0;

        minute = (int) (millisecond / 1000 / 60);
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        second = (int) ((millisecond / 1000) % 60);

        if (hour > 0) {
            return hour + ":"+ getFormatNumber(minute) + ":" + getFormatNumber(second);
        } else {
            return minute + ":" + getFormatNumber(second);
        }
    }

    private static String getFormatNumber(int number) {
        if (number < 10) {
            return "0"+number;
        } else {
            return ""+number;
        }
    }
}
