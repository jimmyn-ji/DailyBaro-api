package com.project.hanfu.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

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
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
