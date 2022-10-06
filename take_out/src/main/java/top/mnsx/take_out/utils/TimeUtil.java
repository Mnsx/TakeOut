package top.mnsx.take_out.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @BelongsProject: second_kill_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/23 17:39
 * @Description: 时间工具类
 */
public class TimeUtil {
    // 时间展示格式
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 将String字符串转换成LocalDateTime
     * @param dateTime 时间字符串
     * @return 返回LocalDateTime
     */
    public static LocalDateTime LDTParse(String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER);
    }

    /**
     * 将String字符串转换成LocalDate
     * @param date 时间字符串
     * @return 返回LocalDate
     */
    public static LocalDate LDParse(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    /**
     * 将String字符串转换成LocalDate
     * @param time 时间字符串
     * @return 返回LocalTime
     */
    public static LocalTime LTParse(String time) {
        return LocalTime.parse(time, FORMATTER);
    }

    /**
     * 将LocalDateTime类转换成字符串
     * @param dateTime LocalDateTime类
     * @return 返回时间字符串
     */
    public static String LDTToString(LocalDateTime dateTime) {
        return FORMATTER.format(dateTime);
    }

    /**
     * 将LocalDate类转换成字符串
     * @param date LocalDate类
     * @return 返回时间字符串
     */
    public static String LDToString(LocalDate date) {
        return FORMATTER.format(date);
    }

    /**
     * 将LocalTime类转换成字符串
     * @param time LocalTime类
     * @return 返回时间字符串
     */
    public static String LTToString(LocalTime time) {
        return FORMATTER.format(time);
    }

    /**
     * 将Date转换成LocalDateTime
     * @param date Date时间类
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 将LocalDateTime转换成Date
     * @param localDateTime LocalDateTime时间类
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
