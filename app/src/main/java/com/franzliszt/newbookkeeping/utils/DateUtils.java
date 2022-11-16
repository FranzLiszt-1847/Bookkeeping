package com.franzliszt.newbookkeeping.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static SimpleDateFormat mSimpleDateFormat = null;

    //获取系统时间
    public static String getCurrentDate() {
        Date d = new Date();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return mSimpleDateFormat.format(d);
    }
    public static String getCurrentTime() {
        Date d = new Date();
        mSimpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return mSimpleDateFormat.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getLongToString(long time) {
        Date d = new Date(time);
        mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        return mSimpleDateFormat.format(d);
    }

    /*将字符串转为时间戳*/
    public static long getStringToLong(String time) {
        mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        Date date = new Date();
        try {
            date = mSimpleDateFormat.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Date getStringToDate(String str){
        mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        try {
            Date date = mSimpleDateFormat.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static long exchangeMouth(long time) {
        long mouth;
        return  mouth = time / (60 * 60 * 1000 * 24)/30;

    }

    public static long exchangeDay(long time) {
        long hour;
        return  hour = time / (60 * 60 * 1000 * 24);
    }

}
