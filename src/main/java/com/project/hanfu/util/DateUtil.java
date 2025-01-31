package com.project.hanfu.util;

import com.project.hanfu.exception.CustomException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;

/*
 * 日期转换工具类
 */
public class DateUtil {
    private static SimpleDateFormat[] sdfs = {new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
            new SimpleDateFormat("yyyy-MM-dd HH:mm"), new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("HH:mm:ss"), new SimpleDateFormat("HH:mm"), new SimpleDateFormat("HHmm"),
            new SimpleDateFormat("yyyy-MM")};

    // 创建 Pattern 对象
    private static String rex = "\\d";

    /**
     * String 转日期
     *
     * @param source
     * @return
     */
    public static Date getDate(String source) {
        System.out.println("时间转换：" + source);
        // 现在创建 matcher 对象  判断是不是时间戳的形式
        if (Pattern.matches(rex, source)) {
            Long time = Long.parseLong(source);
            // 转成直接返回
            return new Date(time);
        } else {
            for (SimpleDateFormat sdf : sdfs) {
                try {
                    // 转成直接返回
                    return sdf.parse(source);
                } catch (ParseException e) {

                }
            }
        }
        // 如果参数绑定失败返回 null
        return null;
    }

    // 获取得String
    public static String getString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    // 获取得String
    public static String getString(Date date) {
        SimpleDateFormat sdf = sdfs[0];
        return sdf.format(date);
    }


    //把日历里面的时分字符串转换成日期格式的
    public static Date changeMinHourToDate(Date date, String hourMin) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result;
        try {
            result = sdf1.parse(sdf.format(date) + " " + hourMin + ":00");
        } catch (Exception e) {
            throw new CustomException("时间转换异常");
        }
        return result;
    }

    //计算两个时间差
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;//计算天
        long nh = 1000 * 60 * 60;//计算小时
        long nm = 1000 * 60;//计算分钟
        long ns = 1000;//计算秒
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = (endDate.getTime() - nowDate.getTime()) / (1000);
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒
        long sec = diff % nd % nh % nm / ns;
        //输出结果
//	    return day + "天" + Math.abs(hour) + "小时" + Math.abs(min) + "分钟" + Math.abs(sec) + "秒";
//	    return Math.abs(hour) + ":" + Math.abs(min) + ":" + Math.abs(sec);
        return Math.abs(diff) + "";
    }

    //时间差，参数秒
    public static String getTimeToDate(Long time){
        StringBuffer stringBuffer = new StringBuffer();
        long d = time/(3600*24);
        if(d > 0){
            stringBuffer.append(d+"天");
        }
        time = time-d*3600*24;
        long h = time/3600;
        if(h > 0){
            stringBuffer.append(h+"时");
        }
        time = time - h*3600;
        long m = time/60;
        if(m > 0){
            stringBuffer.append(m+"分");
        }
        time = time - m*60;
        long s = time;
        if(s > 0 ){
            stringBuffer.append(s+"秒");
        }
        return stringBuffer.toString();
    }


    /**
     * 时间格式转换
     *
     * @param data
     * @param dateFormat
     * @return
     */
    public static String dateToStr(Date data, String dateFormat) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            String dataStr = formatter.format(data);
            return dataStr;
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToStr(Date data) {
        return dateToStr(data, "yyyy-MM-dd HH:mm:ss");
    }


    /**
     * 获取前后时间
     *
     * @param date   时间
     * @param field  单位
     * @param amount 间隔
     * @return
     */
    public static Date getRangeTime(Date date, int field, int amount) {
        try {
            Calendar instance = Calendar.getInstance();
            instance.setTime(date);
            instance.add(field, amount);
            return instance.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static Date strToDate(String dateStr) {
        return strToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间格式转换
     *
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date strToDate(String dateStr, String dateFormat) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date parse = formatter.parse(dateStr);
            return parse;
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
		/*String a = "2018-8-8 12:00:00";
		String b = "2018-8-8 13:59:00";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = format.parse(a);
		Date date2 = format.parse(b);
		System.out.println(getDatePoor(date1,date2));*/
        StringJoiner changreNames = new StringJoiner(",");
        changreNames.add("1");
        changreNames.add("2");
        changreNames.add("3");
        changreNames.add("4");
        System.out.println(changreNames.toString());

    }

    /**
     * 返回时间节点
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param cycle     周期
     * @return
     */
    public static List<Date> getDateList(Date startDate, Date endDate, int cycle) {

        startDate = cleanHours(startDate);
        endDate = cleanHours(endDate);

        Calendar start = getCalendar(startDate);
        //月份差
//        int monthPoor = getMonthPoor(startDate, endDate) + 1;
        // 天数差
        Long daysPoor = getDayPoor(startDate,endDate);
        //分组次数
        Long count = daysPoor / cycle;
        //如果不能被整除
        if (daysPoor % cycle != 0) {
            count += 1;
        }
        List<Date> list = new ArrayList<Date>();
        list.add(startDate);
        for (int i = 0; i < count; i++) {
            //递增周期月
//            start.add(Calendar.MONTH, +cycle);
            //递增周期日
            start.add(Calendar.DAY_OF_MONTH,+cycle);
            startDate = start.getTime();
            //如果开始日期是在结束日期之后
            boolean flag = startDate.after(endDate);
            if (flag) {
                list.add(endDate);
            } else {
                list.add(startDate);
            }
        }
        return list;
    }

    /**
     * 返回一个日历的实例
     *
     * @param date
     * @return
     */
    private static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 计算日期之间的月份差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static int getMonthPoor(Date startDate, Date endDate) {
        Calendar startCalendar = getCalendar(startDate);
        Calendar endCalendar = getCalendar(endDate);
        //年份差
        int yearPoor = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        //月份差
        int monthPoor = yearPoor * 12 + (endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH));
        //日差判断TODO
        if (endCalendar.get(Calendar.DAY_OF_MONTH) < endCalendar.get(Calendar.DAY_OF_MONTH)) {
            monthPoor -= 1;
        }
        return monthPoor;
    }

    /**
     * 计算日期之间的天数差
     */
    public static Long getDayPoor(Date startDate, Date endDate) {
        LocalDate startDateLocal = dateToLocalDate(startDate);
        LocalDate endDateLocal = dateToLocalDate(endDate);
        return endDateLocal.toEpochDay() - startDateLocal.toEpochDay();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转LocalDate
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


    /**
     * localTime 转 Date
     * @param localTime
     * @param localDate
     * @return
     */
    public static Date localTimeToDate(LocalTime localTime,LocalDate localDate){
        LocalDateTime shiftStartDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = shiftStartDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * date 转 localTime
     * @param date
     * @return
     */
    public static LocalTime dateTolocalTimeTo(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalTime localTime = localDateTime.toLocalTime();
        return localTime;
    }


    /**
     * 日期List转字符串List
     *
     * @param dateList
     * @return
     */
    public static List<String> dateListToStrList(List<Date> dateList){
        List<String> list = new ArrayList<>();
        dateList.forEach(date -> {
            list.add(dateToStr(date).substring(0,10));
        });
        return list;
    }

    /**
     * 日期List转字符串List
     *
     * @param dateList
     * @return
     */
    public static List<String> dateListToStrList(List<Date> dateList, String dateFormat) {
        List<String> list = new ArrayList<>();
        dateList.forEach(date -> {
            list.add(dateToStr(date, dateFormat));
        });
        return list;
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }



    /**
     * LocalDateTime转换为Date
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 清除小时
     *
     * @param date
     * @return
     */
    public static Date cleanHours(Date date){
        return localDateToDate(dateToLocalDate(date));
    }


    /**
     * 时间戳转换时分秒格式  例如“25:59:59”
     * 如果大于100小时 显示“99:99:99”
     *
     * @param millis 时间数 单位毫秒
     * @return
     */
    public static String timeLongTOString(Long millis) {
        millis = millis / 1000;
        Long hour = millis / 3600;
        if(hour>=100){
            return "99:99:99";
        }
        if(millis%3600==0){
            return String.format("%02d", hour)+":00:00";
        }
        Long minute = millis % 3600 / 60;
        Long second = millis % 60;
        String time = String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":" + String.format("%02d", second);
        return time;
    }


    public static boolean isWeekend(Date date) {
        Calendar calendar = getCalendar(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    public static int getMinDuration(Date startTime, Date endTime) {
        long startTimeTime = startTime.getTime();
        long endTimeTime = endTime.getTime();
        if (startTimeTime > endTimeTime) {
            endTimeTime = endTimeTime + 24 * 3600 * 1000l;
        }

        int res = (int) ((endTimeTime - startTimeTime) / 60000l);

        return res;
    }
}
