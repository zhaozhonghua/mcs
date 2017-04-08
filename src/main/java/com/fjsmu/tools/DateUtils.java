package com.fjsmu.tools;


import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static Logger logger = Logger.getLogger(DateUtils.class);
    
    private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
        "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm:ss.SSS" };

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "刚刚";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }
    
    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }
    
    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }
    
    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }
    
    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null){
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取日期的年月日
     *
     * @param str
     * @return
     */
    public static String getDateZ(String str){
        Date d = parseDate(str);
        return formatDate(d, "yyyy") + "年"
                + formatDate(d, "MM") + "月"
                + formatDate(d, "dd") + "日";
    }

    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime()-date.getTime();
        return t/(24*60*60*1000);
    }
    
    
    public static Date getDateStart(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return date;
    }
    
    public static Date getDateEnd(Date date) {
        if(date==null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转换日期
     *
     * @param timestampString
     * @return
     */
    public static String timeStamp2Date(String timestampString){
        Long timestamp = Long.parseLong(timestampString)*1000;
        String date = new SimpleDateFormat(parsePatterns[2]).format(new Date(timestamp));
        return date;
    }
    
    /**
     * 计算当前时间的周一
     * @return 
     */
    public static String getWeekFirst(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns[0]);
        
        Calendar ca = Calendar.getInstance(Locale.CHINA);
        ca.setTime(time);
        
        int nowd = ca.get(Calendar.DAY_OF_WEEK) - 1; // 当前星期几
        
        int moveDay = 0;
        if(nowd == 0) { // 如果是星期天
            moveDay = -6; // 往回计算6天，到星期一
        } else {
            moveDay = 1 - nowd; // 计算距离周一有几天
        }
        
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(time);
        currentDate.add(GregorianCalendar.DATE, moveDay);
        Date firstDate = currentDate.getTime();
        
        return sdf.format(firstDate);
    }

    /**
     * 根据生日计算年龄
     *
     * @param dateOfBirth 生日
     * @return
     */
    public static int calcAge(Date dateOfBirth) {
        try{
            int age = 0;
            Calendar born = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            if (dateOfBirth != null) {
                now.setTime(new Date());
                born.setTime(dateOfBirth);
                if (born.after(now)) {
                    return 0;
                }
                age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                    age -= 1;
                }
            }
            return age;
        }catch (Exception e){
            logger.error(e);
            return 0;
        }
    }

    /**
     * 获取指定回退天数的日期值
     * @param pastDayNumber int天数  e.g. -6
     * @return
     */
    public static Date getPastDate(int pastDayNumber) {
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_YEAR, pastDayNumber);
        return calendar.getTime();
    }

    /**
     * 根据秒数计算时长
     *
     * @param duration 时长秒数
     * @return
     */
    public static String getDuration(int duration){
        if(duration > 0){
            int hour, minute, second;
            hour = duration / 3600;
            minute = duration % 3600 /60;
            second = duration % 3600 % 60;
            if (hour > 0){
                return hour + "小时"+minute+"分钟";
            }else{
                return minute+"分钟";
            }
        }else{
            return "时长：未知";
        }
    }

    /**
     * 获取两个时间相差的秒数
     *
     * @param big 时间大的
     * @param small 时间小的
     * @return
     */
    public static long getSecondInterval(Date big, Date small){
        try {
            long interval = (big.getTime() - small.getTime())/1000;
            return interval;
        } catch (Exception e) {
            logger.error(e);
            return -1;
        }
    }

    public static String showTime(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
//            long seconds = toSeconds(delta);
//            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
            return ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    /**
     * 获取时间戳的过期差值
     *
     * @param expire
     * @return
     */
    public static long expireDays(long expire){
        long curr = System.currentTimeMillis()/1000;
        return (expire-curr)/60/60/24;
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


    /**
     * 获取当前的时间戳（单位：秒）
     *
     * @return
     */
    public static long getCurrTimestampSecond(){
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 给当前日期加月
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
//        System.out.println(formatDate(parseDate("2010/3/6")));
//        System.out.println(getDate("yyyy年MM月dd日 E"));
//        long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//        System.out.println(time/(24*60*60*1000));
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(getWeekFirst(sdf.parse("2016-03-16")));
//
//        System.out.println(getDate("yyyyMMdd"));
//
//        System.out.println(parseDate("") == null ? new Date() : "2");
//
//        System.out.println(getPastDate(-365*3));

//        System.out.println(getCurrTimestampSecond());

//        System.out.println(DateUtils.timeStamp2Date("1490085805"));
//
//        System.out.println(DateUtils.toDays(1490085805));
    }
}
