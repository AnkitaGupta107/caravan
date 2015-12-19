package com.peppertap.caravan.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by samvedana on 11/11/14.
 */
public class DateTimeHelper {

    private static String TAG = "DateTimeHelper";


    public static Calendar incrementDateByNMonths(Calendar date, int days) {
        date.add(Calendar.MONTH, days);
        return date;
    }


    public static String incrementDateByNDays(String date, int days){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String output = sdf1.format(c.getTime());

        return output;
    }

    public static Date incrementDateByNHours(Date date, int hrs){
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.setTime(date);
        c.add(Calendar.HOUR, hrs);
        return c.getTime();
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        Date date = c.getTime();
        return sdf.format(date);
    }

    public static int getDaysTillToday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        Date today = c.getTime();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date oldDate = c.getTime();
        double difference = ((double) (today.getTime() - oldDate.getTime())) / (1000 * 60 * 60 * 24);
        if (difference < 0) {
            Log.e(TAG, "in getDaysTillToday : date is in future");
        }
        return (int)Math.floor(difference);
    }

    public static Date getCurrentDateTime() {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        return c.getTime();
    }

    public static String convertDateTimeToFormat(Date date, String format) {
        //for mock mode, Order Date field, this should be
        String ret = "NA";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            ret = sdf.format(date);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getHourRangeForDateTime(Date date) {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.setTime(date);
        String one = c.get(Calendar.HOUR) + " " + (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
        Date date1 = incrementDateByNHours(date, 1);
        c.setTime(date1);
        String two = c.get(Calendar.HOUR) + " " + (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
        return one + " - " + two;
    }

    public static long getTimeNoDate(Date date) {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        c.setTime(date);

        long result = c.get(Calendar.HOUR) * 60 * 60 * 1000 + c.get(Calendar.MINUTE) * 60 * 1000 + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MILLISECOND);
        return result;
    }

    public static Date convertStringToDate(String date, String inputFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        try {
            c.setTime(sdf.parse(date));
            return c.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
