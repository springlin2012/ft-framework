package com.ft.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @descript: 授信业务请求处理
 * @auth: lichunlin
 */
public final class DateUtils {
    public static String FORMATDAY = "yyyyMMdd";
    public static String FORMATDAY_MM = "yyyyMMddHHmm";
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    public static String FORMATDAY_SS = "yyyyMMddHHmmss";
    public static String FORMAT_MM = "yyyy-MM-dd HH:mm";
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * @param startDate
     * @param endDate
     * @param pattern   yyyy-MM-dd or yyyy-MM or yyyy
     * @return
     */
    public static List<String> getBetweeenTime(Date startDate, Date endDate, String pattern) {
        int type = 0;
        if (pattern.contains("dd")) {
            type = Calendar.DATE;
        } else if (pattern.contains("MM")) {
            type = Calendar.MONTH;
        } else if (pattern.contains("yyyy")) {
            type = Calendar.YEAR;
        }
        String strStartDate = format(startDate, pattern);
        String strEndDate = format(endDate, pattern);
        Date theEndDate = parse(strEndDate, pattern);

        Date currDate = parse(strStartDate, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(currDate);
        List<String> ret = new ArrayList<String>();
        while (!cal.getTime().after(theEndDate)) {
            ret.add(format(cal.getTime(), pattern));
            cal.add(type, 1);
        }
        return ret;
    }

    /**
     * 获取当前日期开始指定天数的日期列表
     *
     * @param days
     * @return
     */
    public static List<String> getDaysDates(int days) {
        Date startDate = getDateBeforeOrAfter(days);
        Date endDate = getDateBeforeOrAfter(-1);

        return getBetweeenTime(startDate, endDate, "yyyy-MM-dd");
    }


    /**
     * 得到系统当前日期的前或者后几天
     *
     * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
     * @return Date 返回系统当前日期的前或者后几天
     */
    public static Date getDateBeforeOrAfter(int iDate) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, iDate);
        return cal.getTime();
    }


    /**
     * 获取当前日期开始指定月数后的日期
     *
     * @param currentDay 当前日期
     * @param n
     * @return
     */
    public static Date getNDate(Date currentDay, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDay);
        calendar.add(Calendar.MONTH, n);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 天数
     */
    public static long getDiscrepantDays(Date beginDate, Date endDate) {
        //SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (long) ((endDate.getTime() - beginDate.getTime()) / 1000 / 60 / 60 / 24);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     * @throws ParseException
     */
    public static Date parse(Date date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上减去天数
     *
     * @param date 日期
     * @param n    要减去的天数
     * @return
     */
    public static Date subDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        if (date == null || n == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addHour(Date date, int n) {
        if (date == null || n == 0) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 获取季度(中文)
     *
     * @param date
     * @return
     */
    public static String getSeasonCn(Date date) {
        String season = null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = "一";
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = "二";
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = "三";
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = "四";
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 获取季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 　　 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * countDays(计算连个时间点相隔多少天)
     *
     * @param date1
     * @param date2
     * @return author：zhou'sheng
     */
    public static int countDays(Date date1, Date date2) {

        Date dateA, dateB, temp;

        try {
            dateA = parse(date1, "yyyy-MM-dd");
            dateB = parse(date2, "yyyy-MM-dd");
        } catch (Exception e) {
            return 0;
        }

        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        }

        if (dateA.getTime() < dateB.getTime()) {
            temp = dateA;
            dateA = dateB;
            dateB = temp;
        }

        Calendar cA = Calendar.getInstance();
        Calendar cB = Calendar.getInstance();
        cA.setTime(dateA);
        cB.setTime(dateB);

        long t1 = dateA.getTime();
        long t2 = dateB.getTime();

        return (int) (t1 / 1000 - t2 / 1000) / 3600 / 24;
    }

    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间 为
     * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(Date date1, Date date2, int stype) {
        int n = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }
        n = n - 1;
        if (stype == 2) {
            n = (int) n / 365;
        }
        return n;
    }

    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间 为
     * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static int compareDate(String date1, String date2, int stype) {
        int n = 0;
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            e3.printStackTrace();
        }

        while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }

        n = n - 1;

        if (stype == 2) {
            n = (int) n / 365;
        }

        return n;
    }

    /**
     * compareDate(比较时间大小)
     *
     * @param date1
     * @param date2
     * @return
     * @author：zhou'sheng
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1 == null || null == date2) {
            return 0;
        }
        try {
            if (date1.getTime() > date2.getTime()) {
                return 1;
            } else if (date1.getTime() < date2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }


    public static int getMonths(String date1, String date2) {
        int year, month;
        Date dateA, dateB, temp;

        dateA = parse(date1, "yyyy-MM-dd");
        dateB = parse(date2, "yyyy-MM-dd");

        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        }

        if (dateA.getTime() < dateB.getTime()) {
            temp = dateA;
            dateA = dateB;
            dateB = temp;
        }

        Calendar cA = Calendar.getInstance();
        Calendar cB = Calendar.getInstance();
        cA.setTime(dateA);
        cB.setTime(dateB);

        year = cA.get(Calendar.YEAR) - cB.get(Calendar.YEAR);// 年
        month = cA.get(Calendar.MONTH) - cB.get(Calendar.MONTH);// 月

        if (year > 0) {
            if (month > 0) {
                if (cB.get(Calendar.DATE) > cA.get(Calendar.DATE)) {
                    month--;
                } else if (cB.get(Calendar.DATE) == cA.get(Calendar.DATE)) {

                } else {

                }
            } else if (month == 0) {
                if (cB.get(Calendar.DATE) > cA.get(Calendar.DATE)) {
                    month = -1;
                } else if (cB.get(Calendar.DATE) == cA.get(Calendar.DATE)) {

                } else {

                }
            } else {
                if (cA.get(Calendar.DATE) > cB.get(Calendar.DATE)) {

                } else if (cA.get(Calendar.DATE) == cB.get(Calendar.DATE)) {

                } else {
                    month--;
                }
            }
        } else {
            if (month > 0) {
                if (cB.get(Calendar.DATE) > cA.get(Calendar.DATE)) {
                    month--;
                } else if (cB.get(Calendar.DATE) == cA.get(Calendar.DATE)) {

                } else {

                }
            }
        }

        return year * 12 + month;
    }

    /**
     * 一天开始的一刻
     *
     * @param date
     * @return
     */
    public static Date startOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 一天结束的一刻
     *
     * @param date
     * @return
     */
    public static Date endOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        week = week - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);

        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        week = week - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        Calendar cal = (Calendar) calendar.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek()); // Sunday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK,
                calendar.getFirstDayOfWeek() + 6); // Saturday
        return calendar.getTime();
    }

    /**
     * 取得当前日期所在周的前一周最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfWeek(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.WEEK_OF_YEAR) - 1);
    }

    /**
     * 返回指定日期的月的第一天
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        return calendar.getTime();
    }

    /**
     * 返回指定年月的月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月的最后一天
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定年月的月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(Integer year, Integer month) {
        Calendar calendar = Calendar.getInstance();
        if (year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if (month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        calendar.set(year, month, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的上个月的最后一天
     * @return
     */
    public static Date getLastDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) - 1, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的季的第一天
     * @return
     */
    public static Date getFirstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getFirstDayOfQuarter(calendar.get(Calendar.YEAR),
                getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的季的第一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getFirstDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 1 - 1;
        } else if (quarter == 2) {
            month = 4 - 1;
        } else if (quarter == 3) {
            month = 7 - 1;
        } else if (quarter == 4) {
            month = 10 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getFirstDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的季的最后一天
     * @return
     */
    public static Date getLastDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfQuarter(calendar.get(Calendar.YEAR),
                getQuarterOfYear(date));
    }

    /**
     * 获取下季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfNextQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTime(getLastDayOfQuarter(calendar.get(Calendar.YEAR),
                getQuarterOfYear(date)));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 获取下季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfNextQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 3);
        calendar.setTime(getLastDayOfQuarter(calendar.get(Calendar.YEAR),
                getQuarterOfYear(calendar.getTime())));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }


    /**
     * 返回指定年季的季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 3 - 1;
        } else if (quarter == 2) {
            month = 6 - 1;
        } else if (quarter == 3) {
            month = 9 - 1;
        } else if (quarter == 4) {
            month = 12 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的上一季的最后一天
     * @return
     */
    public static Date getLastDayOfLastQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getLastDayOfLastQuarter(calendar.get(Calendar.YEAR),
                getQuarterOfYear(date));
    }

    /**
     * 返回指定年季的上一季的最后一天
     *
     * @param year
     * @param quarter
     * @return
     */
    public static Date getLastDayOfLastQuarter(Integer year, Integer quarter) {
        Calendar calendar = Calendar.getInstance();
        Integer month = new Integer(0);
        if (quarter == 1) {
            month = 12 - 1;
        } else if (quarter == 2) {
            month = 3 - 1;
        } else if (quarter == 3) {
            month = 6 - 1;
        } else if (quarter == 4) {
            month = 9 - 1;
        } else {
            month = calendar.get(Calendar.MONTH);
        }
        return getLastDayOfMonth(year, month);
    }

    /**
     * 返回指定日期的季度
     *
     * @param date
     * @return
     */
    public static int getQuarterOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) / 3 + 1;
    }


    /**
     * 方法描述：获取两个日期间的月份差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMonthsDiff(String startDate, String endDate) {
        int year, month;
        Date dateA, dateB, temp;

        dateA = parse(startDate, "yyyy-MM-dd");
        dateB = parse(endDate, "yyyy-MM-dd");

        if (dateA.getTime() == dateB.getTime()) {
            return 0;
        }

        Calendar cA = Calendar.getInstance();
        Calendar cB = Calendar.getInstance();
        cA.setTime(dateA);
        cB.setTime(dateB);
        year = cB.get(Calendar.YEAR) - cA.get(Calendar.YEAR);
        month = cB.get(Calendar.MONTH) - cA.get(Calendar.MONTH);
        return year * 12 + month;

    }

    /**
     * 方法描述：列出两个日期所涉及到的所有月份，格式为yyyyMM
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getMonthsList(String startDate, String endDate) {
        List<String> monthsList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        Date date = parse(startDate, FORMAT_SHORT);
        cal.setTime(date);
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMM");
        int months = getMonthsDiff(startDate, endDate);
        for (int i = 0; i <= months; i++) {
            cal.setTime(date);
            cal.add(Calendar.MONTH, i);
            monthsList.add(simple.format(cal.getTime()));
        }
        return monthsList;
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static String getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static String getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return format(currYearFirst, FORMATDAY);
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return format(currYearLast, FORMATDAY);
    }

    /**
     * 获取两个时间范围之间的所有时间 包括起始时间
     *
     * @param date1 时间 yyyyMMdd
     * @param date2 时间 yyyyMMdd
     * @return
     * @throws ParseException
     */
    public static List<String> process(String date1, String date2)
            throws ParseException {
        List<String> timeList = new ArrayList<String>();
        String dateFormat = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        if (date1.equals(date2)) {
            timeList.add(date1);
            return timeList;
        }
        String tmp = "";
        if (date1.compareTo(date2) > 0) { // 确保 date1的日期不晚于date2
            tmp = date1;
            date1 = date2;
            date2 = tmp;
        }
        tmp = format.format(strToDate(date1).getTime() + 3600 * 24 * 1000);
        timeList.add(date1);
        while (tmp.compareTo(date2) <= 0) {
            timeList.add(tmp);
            tmp = format.format(strToDate(tmp).getTime() + 3600 * 24 * 1000);
        }
        return timeList;
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     * @throws ParseException
     */
    public static Date strToDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(
                FORMATDAY);
        Date date = format.parse(str);
        return date;
    }

    /**
     * 在日期上增加分钟
     *
     * @param date 日期
     * @param n    要增加的分钟
     * @return
     */
    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    /**
     * 得到昨天日期
     *
     * @return
     */
    public static String getYesterdayDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        //return "2016-04-01";
        //return "2016-04-02";
        //return "2016-04-03";
        //return "2016-04-04";
        return simple.format(date);
    }

    /**
     * 根据日期字符串返回增加天数的日期字符串
     * addDay:(这里用一句话描述这个方法的作用). <br/>
     *
     * @param dateString
     * @param n
     * @return
     * @author xn054134
     * @since JDK 1.7
     */
    public static String addDay(String dateString, int n) {
        Calendar cal = Calendar.getInstance();
        Date date = parse(dateString, FORMATDAY);
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(cal.getTime());
    }

    /**
     * 方法描述：将yyyyMMdd格式字符串转换为yyyy-MM-dd字符串
     *
     * @param dateStr，格式yyyyMMdd
     * @return
     */
    public static String convertToFormatShort(String dateStr) {
        Calendar cal = Calendar.getInstance();
        Date date = parse(dateStr, FORMATDAY);
        cal.setTime(date);
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(cal.getTime());
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(process("20160101", "20161231").size());
        System.out.println(DateUtils.addDay("20161001", 1));
        System.out.println(DateUtils.convertToFormatShort("20161001"));
    }

}