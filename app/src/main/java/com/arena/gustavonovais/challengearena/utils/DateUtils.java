package com.arena.gustavonovais.challengearena.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gustavo.silva on 04/11/16.
 */

public class DateUtils {

    public static final String format = "dd/MM/yyyy";

    public static String parseDateFormat(Date date) {
        String formatedDate = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            formatedDate = sdf.format(date);
        }
        return formatedDate;
    }


    public static String converDate(double time) {
        java.util.Date date = new java.util.Date((long) time * 1000);
        return parseDateFormat(date);
    }

}