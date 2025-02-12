package cc.wangzijie.demo.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    private static String getFormat(String format) {
        return null == format ? DateFormat.YYYY_MM_DD_HH_MM_SS : format;
    }

    public static ZoneOffset getZoneOffset() {
        return ZoneOffset.of("+8");
    }

    /**
     * Date时间类转LocalDateTime
     *
     * @param date Date时间类
     * @return LocalDateTime
     */
    public static LocalDateTime ofDate(Date date) {
        return date.toInstant().atOffset(getZoneOffset()).toLocalDateTime();
    }

    /**
     * LocalDateTime转时间格式字符串
     *
     * @param localDateTime 时间
     * @param format        格式化
     * @return string
     */
    public static String formatToString(LocalDateTime localDateTime, String format) {
        return localDateTime.format(DateTimeFormatter.ofPattern(getFormat(format)));
    }

    /**
     * LocalTime转时间格式字符串
     *
     * @param localTime 时间
     * @param format    格式化
     * @return string
     */
    public static String formatToString(LocalTime localTime, String format) {
        return localTime.format(DateTimeFormatter.ofPattern(getFormat(format)));
    }

    /**
     * Date转时间格式字符串
     *
     * @param date   时间
     * @param format 格式化
     * @return string
     */
    public static String formatToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        return ofDate(date).format(DateTimeFormatter.ofPattern(getFormat(format)));
    }

    public static String formatToStringOrNull(Date date, String format, String onNull) {
        if (date == null) {
            return onNull;
        }
        return DateUtils.formatToString(date, format);
    }

    public static String nowStr(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(getFormat(format)));
    }

}
