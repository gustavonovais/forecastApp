package com.arena.gustavonovais.challengearena.utils;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gustavo.silva on 04/11/16.
 */

public class DateUtils {

    public static final String format = "dd/MM/yyyy";
    public static final String american_format = "yyyy-MM-dd";

    public static final String american_format_full = "yyyy-MM-dd hh:mm:ss";

    public static Date formatDate(String dateFormated) {
        Date date = null;
        if (dateFormated != null && !dateFormated.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setLenient(false);
                date = sdf.parse(dateFormated);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

    public static Date formatAmericanDate(String dateFormated) {
        Date date = null;
        if (dateFormated != null && !dateFormated.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(american_format);
                sdf.setLenient(false);
                date = sdf.parse(dateFormated);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return date;
    }

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