package com.softserve.osbb.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nazar.dovhyy on 11.09.2016.
 */
public class CustomDateFormatter {

    private static ThreadLocal<DateFormat> DATE_FORMAT_CACHE = new ThreadLocal<>();

    public static String marshal(Date date) {
        return getFormat().format(date);
    }

    public static Date unmarshal(String value) throws Exception {
        return isNullOrEmpty(value) ? null : getFormat().parse(value);
    }

    private static DateFormat getFormat() {
        DateFormat dateFormat = DATE_FORMAT_CACHE.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD);
            DATE_FORMAT_CACHE.set(dateFormat);
        }

        return dateFormat;
    }

    private static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
