package com.cloud.core.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.enums.DisplayPeriodTimeType;
import com.cloud.core.logger.Logger;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 上午8:42:07
 * @Description: 日期工具类
 * @Modifier:
 * @ModifyContent:
 */
public class DateUtils {

    public static String getDateTime(DateFormatEnum format, String separator, Calendar calendar) {
        String fm = TextUtils.isEmpty(separator) ? format.getValue() :
                MessageFormat.format(format.getValue(), separator);
        SimpleDateFormat sdf = new SimpleDateFormat(fm);
        return sdf.format(calendar.getTime());
    }

    public static String getDateTime(DateFormatEnum format, Calendar calendar) {
        return getDateTime(format, "-", calendar);
    }

    public static String getDateTime(String format, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    public static String getDateTime(DateFormatEnum format) {
        return getDateTime(format, Calendar.getInstance());
    }

    public static String getDate(DateFormatEnum format, String separator, String dateStr) {
        String fm = MessageFormat.format(format.getValue(), separator);
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date parse = null;
        try {
            parse = sdf.parse(dateStr);
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return parse == null ? "" : sdf.format(parse);
    }

    public static String getDate(DateFormatEnum format, String dateStr) {
        return getDate(format, "-", dateStr);
    }

    public static String getDate(DateFormatEnum format, String separator, long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timeStamp));
        return getDateTime(format, separator, calendar);
    }

    public static String getDate(DateFormatEnum format, long timeStamp) {
        return getDate(format, "-", timeStamp);
    }

    /**
     * 得到某年某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        if (month == 0) {
            return 0;
        }
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return (c.get(Calendar.MONTH) + 1);
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static int getCurrDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrHour() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrMinute() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MINUTE);
    }

    /**
     * 得到几天/周/月/年后的日期，整数往后推,负数往前推进
     *
     * @param calendarType Calendar.DATE,Calendar.WEEK_OF_YEAR,Calendar.MONTH,Calendar.
     *                     YEAR
     * @param next
     * @return
     */
    public static String getDayByDate(DateFormatEnum format,
                                      String separator,
                                      int calendarType,
                                      int next) {
        String fm = MessageFormat.format(format.getValue(), separator);
        SimpleDateFormat sdf = new SimpleDateFormat(fm);
        Date date = getDateByCalendar(calendarType, next);
        String dateString = sdf.format(date);
        return dateString;
    }

    public static String getDayByDate(DateFormatEnum format,
                                      int calendarType,
                                      int next) {
        return getDayByDate(format, "-", calendarType, next);
    }

    /**
     * 得到几天/周/月/年后的日期，整数往后推,负数往前推进
     *
     * @param calendarType Calendar.DATE,Calendar.WEEK_OF_YEAR,Calendar.MONTH,Calendar.
     * @param next
     * @return
     */
    public static Calendar getCalendar(int calendarType, int next) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarType, next);
        return calendar;
    }

    /**
     * 得到几天/周/月/年后的日期，整数往后推,负数往前推进
     *
     * @param milliseconds
     * @param calendarType Calendar.DATE,Calendar.WEEK_OF_YEAR,Calendar.MONTH,Calendar.
     * @param next
     * @return
     */
    public static Calendar getCalendar(long milliseconds, int calendarType,
                                       int next) {
        Calendar calendar = Calendar.getInstance();
        if (milliseconds > 0) {
            calendar.setTimeInMillis(milliseconds);
        }
        calendar.add(calendarType, next);
        return calendar;
    }

    /**
     * 得到几天/周/月/年后的日期，整数往后推,负数往前推进
     *
     * @param calendarType Calendar.DATE,Calendar.WEEK_OF_YEAR,Calendar.MONTH,Calendar.
     * @param next
     * @return
     */
    public static Date getDateByCalendar(int calendarType, int next) {
        Calendar calendar = getCalendar(calendarType, next);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取毫秒时间差
     *
     * @param start 开始毫秒数
     * @param end   结束毫秒数
     * @return
     */
    public static long getMSTimeDiff(long start, long end) {
        if (start >= end) {
            return 0;
        }
        return end - start;
    }

    /**
     * 获取秒时间差
     *
     * @param start 开始毫秒数
     * @param end   结束毫秒数
     * @return
     */
    public static long getScondTimeDiff(long start, long end) {
        long diff = getMSTimeDiff(start, end);
        if (diff <= 0) {
            return 0;
        }
        return (long) (diff / 1000);
    }

    /**
     * 获取分时间差
     *
     * @param start 开始毫秒数
     * @param end   结束毫秒数
     * @return
     */
    public static int getMinuteTimeDiff(long start, long end) {
        long diff = getMSTimeDiff(start, end);
        if (diff <= 0) {
            return 0;
        }
        return (int) (diff / (1000 * 60));
    }

    /**
     * 获取小时时间差
     *
     * @param start 开始毫秒数
     * @param end   结束毫秒数
     * @return
     */
    public static int getHourTimeDiff(long start, long end) {
        long diff = getMSTimeDiff(start, end);
        if (diff <= 0) {
            return 0;
        }
        return (int) (diff / (1000 * 60 * 60));
    }

    /**
     * 获取天时间差
     *
     * @param start 开始毫秒数
     * @param end   结束毫秒数
     * @return
     */
    public static int getDayTimeDiff(long start, long end) {
        long diff = getMSTimeDiff(start, end);
        if (diff <= 0) {
            return 0;
        }
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取显示时间段(如3分钟前，1小时前，昨天等)
     *
     * @return
     */
    public static String getDisplayPeriodTime(long timeStamp, DisplayPeriodTimeType displayPeriodTimeType) {
        try {
            long timediff = System.currentTimeMillis() - timeStamp;
            if (timediff > 0) {
                int curryear = DateUtils.getCurrentYear();
                int currmonth = DateUtils.getCurrentMonth();
                Calendar currdate = DateUtils.getCalendar(timeStamp, Calendar.DATE, 0);
                int minutes = (int) (timediff / 60000);
                if (minutes < 60) {
                    //在一小时内
                    if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.MMDDHHMM.getValue());
                        return sdf.format(currdate.getTime());
                    } else {
                        if (curryear > currdate.get(Calendar.YEAR) || currmonth > currdate.get(Calendar.MONTH)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.YYMMDD.getValue());
                            return sdf.format(currdate.getTime());
                        } else {
                            if (minutes > 1) {
                                return String.format("%s 分钟前", minutes);
                            } else {
                                return "刚刚";
                            }
                        }
                    }
                } else if (minutes < 120) {
                    //在两小时内
                    if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.MMDDHHMM.getValue());
                        return sdf.format(currdate.getTime());
                    } else {
                        if (curryear > currdate.get(Calendar.YEAR) || currmonth > currdate.get(Calendar.MONTH)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.YYMMDD.getValue());
                            return sdf.format(currdate.getTime());
                        } else {
                            return "两小时前";
                        }
                    }
                } else if (minutes < 60 * 24) {
                    //在一天内
                    if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.MMDDHHMM.getValue());
                        return sdf.format(currdate.getTime());
                    } else {
                        if (curryear > currdate.get(Calendar.YEAR) || currmonth > currdate.get(Calendar.MONTH)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.YYMMDD.getValue());
                            return sdf.format(currdate.getTime());
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.HHmm.getValue());
                            if ((minutes / 60) > getCurrHour()) {
                                return String.format("昨天 %s", sdf.format(currdate.getTime()));
                            } else {
                                return sdf.format(currdate.getTime());
                            }
                        }
                    }
                } else if (minutes < 60 * 24 * 2) {
                    //在2天内
                    if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.MMDDHHMM.getValue());
                        return sdf.format(currdate.getTime());
                    } else {
                        if (curryear > currdate.get(Calendar.YEAR) || currmonth > currdate.get(Calendar.MONTH)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.YYMMDD.getValue());
                            return sdf.format(currdate.getTime());
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.HHmm.getValue());
                            return String.format("昨天 %s", sdf.format(currdate.getTime()));
                        }
                    }
                } else {
                    if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.MMDDHHMM.getValue());
                        return sdf.format(currdate.getTime());
                    } else {
                        if (curryear > currdate.get(Calendar.YEAR) || currmonth > currdate.get(Calendar.MONTH)) {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.YYMMDD.getValue());
                            return sdf.format(currdate.getTime());
                        } else {
                            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.MMDD.getValue());
                            return sdf.format(currdate.getTime());
                        }
                    }
                }
            } else {
                if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                    SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.HHmm.getValue());
                    return sdf.format(new Date());
                } else {
                    return "刚刚";
                }
            }
        } catch (Exception e) {
            if (displayPeriodTimeType == DisplayPeriodTimeType.MMDDHHMM) {
                SimpleDateFormat sdf = new SimpleDateFormat(DateFormatEnum.HHmm.getValue());
                return sdf.format(new Date());
            } else {
                return DateUtils.getDate(DateFormatEnum.YYYYMMDDHHMMSS, timeStamp);
            }
        }
    }

    /**
     * 获取显示时间段(如3分钟前，1小时前，昨天等)
     *
     * @return
     */
    public static String getDisplayPeriodTime(long timeStamp) {
        return getDisplayPeriodTime(timeStamp, DisplayPeriodTimeType.None);
    }

    /**
     * 月日时分秒，0-9前补0
     */
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

    /**
     * 截取掉前缀0以便转换为整数
     *
     * @see #fillZero(int)
     */
    public static int trimZero(String text) {
        if (text.startsWith("0")) {
            text = text.substring(1);
        }
        return Integer.parseInt(text);
    }

    /**
     * 功能：判断日期是否和当前date对象在同一天。
     * 参见：http://www.cnblogs.com/myzhijie/p/3330970.html
     *
     * @param date 比较的日期
     * @return boolean 如果在返回true，否则返回false。
     */
    public static boolean isSameDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date);
        return (nowCalendar.get(Calendar.ERA) == newCalendar.get(Calendar.ERA) &&
                nowCalendar.get(Calendar.YEAR) == newCalendar.get(Calendar.YEAR) &&
                nowCalendar.get(Calendar.DAY_OF_YEAR) == newCalendar.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr    时间字符串
     * @param dataFormat 当前时间字符串的格式。
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr, String dataFormat) {
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
            Date date = dateFormat.parse(dateStr);
            return new Date(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss字符串转换成日期<br/>
     *
     * @param dateStr yyyy-MM-dd HH:mm:ss字符串
     * @return Date 日期 ,转换异常时返回null。
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }
}