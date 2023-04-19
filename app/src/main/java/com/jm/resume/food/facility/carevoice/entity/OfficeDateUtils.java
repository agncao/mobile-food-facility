package com.jm.resume.food.facility.carevoice.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class OfficeDateUtils {
    public static Date parseTime(String dateTimeStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormat.setLenient(false);
            Date date = dateFormat.parse(dateTimeStr);
            return date;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid time format: " + dateTimeStr, e);
        }
    }

    /**
     * the date is work day or not
     * @param date
     * @return
     */
    public static boolean isWorkDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    /**
     * set the date time with a new value
     * @param date
     * @param timeStr
     * @return
     */
    public static Date parseTime(Date date, String timeStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            String dateStr = dateFormat.format(date);
            String dateTimeStr = dateStr + " " + timeStr;
            return parseTime(dateTimeStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid time format: " + timeStr, e);
        }
    }

    /**
     * add hours to a date
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }
}
