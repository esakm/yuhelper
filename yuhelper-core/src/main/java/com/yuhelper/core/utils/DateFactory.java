package com.yuhelper.core.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateFactory {


    /**
     * Returns the current timestamp + 30 minutes. Use this to generate expiry dates for any tokens.
     *
     * @return the current time with 30 minutes added.
     */
    public static Date getShortTokenExpiryDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 30);
        return calendar.getTime();
    }

}
