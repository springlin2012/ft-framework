/*
 * Copyright (c) 2019 KLTB, Inc. All rights reserved.
 *
 * @author lichunlin
 */
package com.kltb.framework.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final long MS = 1L;
    public static final long SECOND_MS = 1000L;
    public static final long MINUTE_MS = 60000L;
    public static final long HOUR_MS = 3600000L;
    public static final long DAY_MS = 86400000L;
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    private static final SimpleDateFormat NORM_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat NORM_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat NORM_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat HTTP_DATETIME_FORMAT;

    static {
        HTTP_DATETIME_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    }

    public DateUtil() {}

    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parse(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateStr);
    }

    public static Date lastMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date lastYearEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.set(2, 0);
        calendar.add(5, -1);
        return calendar.getTime();
    }

    public static Date firstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date firstDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.set(2, 0);
        return calendar.getTime();
    }

    public static int compareDay(Date d1, Date d2) {
        Calendar time = Calendar.getInstance();
        time.setTime(d1);
        time.set(14, 0);
        time.set(13, 0);
        time.set(12, 0);
        time.set(11, 0);
        Date newD1 = time.getTime();
        time.setTime(d2);
        time.set(14, 0);
        time.set(13, 0);
        time.set(12, 0);
        time.set(11, 0);
        Date newD2 = time.getTime();
        return newD1.compareTo(newD2);
    }

    public static Date getCurrDateTime() {
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        time.set(14, 0);
        return time.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, days);
        return calendar.getTime();
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(13, seconds);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, minutes);
        return calendar.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(11, hours);
        return calendar.getTime();
    }

    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, months);
        return calendar.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, years);
        return calendar.getTime();
    }

    public static Date getCurrDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return calendar.getTime();
    }

    public static Date nextDay(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(5, 1);
        return calendar.getTime();
    }

    public static Date firstDayOfNextQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curMonth = calendar.get(2);
        int firstMonthOfNextQuarter = curMonth / 3 * 3 + 3;
        calendar.add(2, firstMonthOfNextQuarter - curMonth);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date clearTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date clearMilliSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static int getMonthIntervals(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int month1 = calendar.get(1) * 12 + calendar.get(2);
        calendar.setTime(date2);
        int month2 = calendar.get(1) * 12 + calendar.get(2);
        return month1 - month2;
    }

    public static int getYearIntervals(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(1);
        calendar.setTime(date2);
        int year2 = calendar.get(1);
        return year1 - year2;
    }

    public static int getQuarterIntervals(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int month1 = calendar.get(1) * 12 + calendar.get(2);
        calendar.setTime(date2);
        int month2 = calendar.get(1) * 12 + calendar.get(2);
        return month1 / 3 - month2 / 3;
    }

    public static long getDayIntervals(Date date1, Date date2) {
        return (date1.getTime() - 28800L) / 86400000L - (date2.getTime() - 28800L) / 86400000L;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return getIntDate(date1.getTime()) == getIntDate(date2.getTime());
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        return getMonthIntervals(date1, date2) == 0;
    }

    public static boolean isSameYear(Date date1, Date date2) {
        return getYearIntervals(date1, date2) == 0;
    }

    public static boolean isSameQuarter(Date date1, Date date2) {
        return getQuarterIntervals(date1, date2) == 0;
    }

    public static int getYear(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(1);
    }

    public static int getYearAndMon(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(1) * 100 + calendar.get(2) + 1;
    }

    public static int getYearAndQuarter(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(1) * 100 + calendar.get(2) / 3 + 1;
    }

    public static int getIntDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(1) * 10000 + (calendar.get(2) + 1) * 100 + calendar.get(5);
    }

    public static String now() {
        return formatDateTime(new Date());
    }

    public static String today() {
        return formatDate(new Date());
    }

    public static String formatDateTime(Date date) {
        return NORM_DATETIME_FORMAT.format(date);
    }

    public static String formatHttpDate(Date date) {
        return HTTP_DATETIME_FORMAT.format(date);
    }

    public static String formatDate(Date date) {
        return NORM_DATE_FORMAT.format(date);
    }

    public static Date parseDateTime(String dateString) {
        try {
            return NORM_DATETIME_FORMAT.parse(dateString);
        } catch (ParseException var2) {
            return null;
        }
    }

    public static Date parseDate(String dateString) {
        try {
            return NORM_DATE_FORMAT.parse(dateString);
        } catch (ParseException var2) {
            return null;
        }
    }

    public static Date parseTime(String timeString) {
        try {
            return NORM_TIME_FORMAT.parse(timeString);
        } catch (ParseException var2) {
            return null;
        }
    }

    public static Date parse(String dateStr) throws Exception {
        int length = dateStr.length();

        try {
            if (length == "yyyy-MM-dd HH:mm:ss".length()) {
                return parseDateTime(dateStr);
            }

            if (length == "yyyy-MM-dd".length()) {
                return parseDate(dateStr);
            }

            if (length == "HH:mm:ss".length()) {
                return parseTime(dateStr);
            }
        } catch (Exception e) {
            throw e;
        }

        return null;
    }

    public static Date yesterday() {
        return offsiteDate(new Date(), 6, -1);
    }

    public static Date lastWeek() {
        return offsiteDate(new Date(), 3, -1);
    }

    public static Date lastMouth() {
        return offsiteDate(new Date(), 2, -1);
    }

    public static Date offsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }

    public static long diff(Date minuend, Date subtrahend, long diffField) {
        long diff = minuend.getTime() - subtrahend.getTime();
        return diff / diffField;
    }

    public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }
}
