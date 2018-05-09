package com.cctv.ssfs.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间工具类
 */
public class DateUtil {
    private static final String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    private static final String FORMAT_MDHM = "MM-dd HH:mm";
    private static final String FORMAT_DHM = "dd日HH:mm";
    public static final String FORMAT_HMS = "HH:mm:ss";
    public static final String FORMAT_HM = "HH:mm";
    public static final String FORMAT_YM = "yyyy-MM";


    public static String getSmartTime(String time) {
        Date date = getDate(time);

        int offset = getDaysBetween(date, getDateNow());
        if (offset == 0) {
            return getTime(date, FORMAT_HM);
        } else if (offset == 1) {
            return "昨日 " + getTime(date, FORMAT_HM);
        }

        return getTime(date, FORMAT_MDHM);
    }

    public static String getLootNumTime(String time) {
        Date date = getDate(time);
        int offset = getDaysBetween(date, getDateNow());
        if (offset == 0) {
            return "今日" + getTime(date, FORMAT_HM);
        } else if (offset == -1) {
            return "明日" + getTime(date, FORMAT_HM);
        } else {
            return getTime(date, FORMAT_DHM);
        }
    }

    public static String getLootNumDay(String time) {
        Date date = getDate(time);
        int offset = getDaysBetween(date, getDateNow());

        if (offset == 0) {
            return "今日";
        } else if (offset == 1) {
            return "昨日";
        } else {
            return getTime(date, "dd") + "日";
        }
    }

    public static String getTime(String time) {
        return getTime(time, FORMAT_YMDHMS);
    }

    public static String getTime(String time, String format) {
        Date date = getDate(time);
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        return df.format(date);
    }

    private static String getTime(Date date, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.CHINA);
        return df.format(date);
    }


    private static int getDaysBetween(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public static Date getDate(String time) {
        long ltime = 0;
        try {
            if (time != null) {
                if (time.length() < 13) {
                    StringBuilder sb = new StringBuilder();
                    int count = 13 - time.length();
                    for (int i = 0; i < count; i++) {
                        sb.append('0');
                    }
                    time += sb.toString();
                } else if (time.length() > 13) {
                    time = time.substring(0, 13);
                }
            }
            ltime = Long.parseLong(time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TimeZone zone = TimeZone.getTimeZone("GMT+0800");
        Calendar c = Calendar.getInstance(zone);
        c.setTimeInMillis(ltime);

        return c.getTime();
    }

    /**
     * 将字符串时间转成时间戳
     *
     * @param timeStr 字符串时间 如 XXXX年XX月XX日
     * @param format  对应的时间格式解析器
     * @return 时间戳
     */
    public static long stringToTime(String timeStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        try {
            Date date = sdf.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static Date getDateNow() {
        TimeZone zone = TimeZone.getTimeZone("GMT+0800");
        Calendar c = Calendar.getInstance(zone);
        return c.getTime();
    }

    // 根据日期计算星期
    @SuppressLint("SimpleDateFormat")
    public static String getWeek(String sTime) {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(sTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
        return String.format("周%s", weeks[c.get(Calendar.DAY_OF_WEEK) - 1]);
    }

    // 根据日期计算星期
    @SuppressLint("SimpleDateFormat")
    public static int getWeekNum(String sTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(sTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK);
    }


    // 根据日期计算月份
    @SuppressLint("SimpleDateFormat")
    public static String getMonth(String sTime) {
        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
        int currentYear = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        String time = sTime.substring(5, sTime.length());
        int id = Integer.parseInt(time);
        String[] months = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        String month = months[id - 1];
        if (sTime.startsWith(String.valueOf(currentYear))) {
            return month + "月";
        } else {
            return sTime;
        }
    }

    public static String getNumDay(String time) {
        return time.substring(time.length() - 2, time.length());
    }

    /**
     * 获取离当前系统时间的长度
     */
    public static String getDistanceNow(long time) {
        long currentTime = System.currentTimeMillis() / 1000;
        int distance = (int) (currentTime - time);
        if (distance < 60 * 5) {
            return "刚刚";
        } else if (distance < 60 * 60) {
            return distance / 60 + "分钟前";
        } else if (distance < 60 * 60 * 24) {
            return distance / 60 / 60 + "小时前";
        } else if (distance < 60 * 60 * 24 * 7) {
            return distance / 60 / 60 / 24 + "天前";
        }
        return getDayYearTime(String.valueOf(time));
    }

    public static String getLayerTime(String time) {
        Date date = getDate(time);
        Calendar setCalendar = Calendar.getInstance();//设置日期
        setCalendar.setTime(date);

        Calendar nowCalendar = Calendar.getInstance();//当前日期
        nowCalendar.setTime(getDateNow());
        if (nowCalendar.get(Calendar.YEAR) != setCalendar.get(Calendar.YEAR)) {
            return getTime(date, FORMAT_YMDHM);
        } else if (nowCalendar.get(Calendar.DAY_OF_YEAR) != setCalendar.get(Calendar.DAY_OF_YEAR)) {
            return getTime(date, FORMAT_MDHM);
        } else {
            return getTime(date, FORMAT_HM);
        }
    }


    public static String getDayYearTime(String time) {
        Date date = getDate(time);
        Calendar setCalendar = Calendar.getInstance();//设置日期
        setCalendar.setTime(date);

        Calendar nowCalendar = Calendar.getInstance();//当前日期
        nowCalendar.setTime(getDateNow());
        if (nowCalendar.get(Calendar.YEAR) == setCalendar.get(Calendar.YEAR)) {
            return getTime(date, "MM-dd");
        } else {
            return getTime(date, FORMAT_YMD);
        }
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014.06.14"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy.MM.dd");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    /**
     * 获取制定两个时间点相差天数
     *
     * @param timeOld 旧时间点
     * @param timeNew 新时间点
     * @return 相差天数
     */
    public static int getDiffDay(long timeOld, long timeNew) {
        return (int) ((timeNew - timeOld) / 1000 / 60 / 60 / 24);
    }

    /**
     * 1分钟、1小时、1天、月，年
     */
    private final static long MINUTE = 60 * 1000;
    private final static long HOUR = 60 * MINUTE;
    private final static long DAY = 24 * HOUR;
    private final static long MONTH = 31 * DAY;
    private final static long YEAR = 12 * MONTH;
    /**
     *    * 返回文字描述的日期
     *    *
     *    * @param date
     *    * @return
     *    
     */
    public static String getTimeFormatText(long date){
        if (date == 0) {
            return null;
        }
        long diff = System.currentTimeMillis() - date;
        long r = 0;
        if (diff > YEAR) {
            r = (diff / YEAR);
            return r + "年前";
        }
        if (diff > MONTH) {
            r = (diff / MONTH);
            return r + "个月前";
        }
        if (diff > DAY) {
            r = (diff / DAY);
            return r + "天前";
        }
        if (diff > HOUR) {
            r = (diff / HOUR);
            return r + "个小时";
        }
        if (diff > MINUTE) {
            r = (diff / MINUTE);
            return r + "分钟";
        }
        return "刚刚";
    }
}
